import { Injectable, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IContainer } from '../../../../../../../model/IContainer';
import { MetaInfo } from '../../../../../../../model/meta/field-meta';
import { Id } from '../../../../../../../util/id';
import { Objects } from '../../../../../../../util/objects';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { GraphTransformer } from '../../tool-pallette/util/graph-transformer';
import { GraphicalEditorService } from '../../graphical-editor/services/graphical-editor.service';
import { ModelImageService } from '../../graphical-editor/services/model-image.service';
import { Folder } from 'src/app/model/Folder';

@Injectable()
export abstract class ContentContainerBase<T extends IContainer> implements OnInit {

    protected abstract get condition(): (element: IContainer) => boolean;
    public contents: IContainer[];

    public get folders(): Folder[] {
        return this.contents as Folder[];
    }

    protected _parent: IContainer;

    protected setParent(parent: IContainer) {
        this._parent = parent;
        this.readContents();
    }

    constructor(
        protected dataService: SpecmateDataService,
        protected navigator: NavigatorService,
        protected translate: TranslateService,
        protected modal: ConfirmationModal,
        private clipboardService: ClipboardService,
        protected graphicalEditorService: GraphicalEditorService,
        protected modelImageService: ModelImageService) {
    }

    public async ngOnInit(): Promise<void> {
        await this.readContents();
    }

    public async create(name: string): Promise<void> {
        if (name && this.validPattern(name)) {
            const element = await this.createElement(name);
            if (element != undefined) {
                await this.navigator.navigate(element);
            }
        }
    }

    public abstract createElement(name: string): Promise<T>;

    public async duplicate(element: IContainer): Promise<void> {
        await this.dataService.performOperations(element.url, 'duplicate');
        await this.dataService.commit(this.translate.instant('duplicate'));
        await this.readContents();
    }

    public async recycle(element: IContainer,
        message: string = this.translate.instant('doYouReallyWantToDelete', { name: element.name })): Promise<void> {
        try {
            await this.modal.openOkCancel('ConfirmationRequired', message);
            await this.dataService.recycleElement(element.url);
            await this.readContents();
        } catch (e) { }
    }

    protected async readContents(): Promise<void> {
        this.contents = undefined;
        const contents = await this.dataService.readContents(this._parent.url, false);
        this.contents = contents.filter(this.condition);
    }

    private get clipboardModel(): IContainer {
        if (this.canPaste) {
            return this.clipboardService.clipboard[0] as IContainer;
        }
        return undefined;
    }

    public get clipboardModelName(): string {
        if (this.canPaste) {
            return this.clipboardModel.name;
        }
        return this.translate.instant('pasteAsName');
    }

    public async copy(model: IContainer): Promise<void> {
        const copy = Objects.clone(model) as IContainer;
        copy.description = model.description;
        copy.id = Id.uuid;
        copy.name = this.translate.instant('copyOf') + ' ' + model.name;
        this.clipboardService.clipboard = [copy];
    }

    public async paste(name: string): Promise<void> {
        if (!this.canPaste || !this.validPattern(name)) {
            return;
        }
        const model = this.clipboardModel;
        const pasteName = name || this.clipboardModelName;
        const copy = await this.createElement(pasteName);
        copy.description = model.description;

        const contents = await this.dataService.readContents(model.url, true);
        const transformer = new GraphTransformer(this.dataService, undefined, copy);

        const compoundId = Id.uuid;
        await this.dataService.updateElement(copy, true, compoundId);
        await transformer.cloneSubgraph(contents, compoundId, true, 0);
        await this.dataService.commit(this.translate.instant('paste'));
        await this.readContents();
        this.clipboardService.clear();
    }

    public get canPaste(): boolean {
        return this.clipboardService.clipboard.length === 1 && this.condition(this.clipboardService.clipboard[0]);
    }

    public isCreateButtonEnabled(name: string): boolean {
        if (!this.validPattern(name) || name === undefined || name.length === 0) {
            return false;
        }
        return true;
    }
    public validPattern(name: string): boolean {
        let validPattern = MetaInfo.INamed[0].allowedPattern;
        let validName: RegExp = new RegExp(validPattern);
        if (name.match(validName)) {
            return true;
        }
        return false;
    }
    public get errorMessage(): string {
        return this.translate.instant('invalidCharacter');
    }

    public showElement(element: IContainer): boolean {
        return !element.recycled;
    }
}
