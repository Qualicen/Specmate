import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';
import { TestSpecificationFactory } from '../../../../../../../factory/test-specification-factory';
import { IContainer } from '../../../../../../../model/IContainer';
import { TestCase } from '../../../../../../../model/TestCase';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { Id } from '../../../../../../../util/id';
import { Sort } from '../../../../../../../util/sort';
import { Type } from '../../../../../../../util/type';
import { Url } from '../../../../../../../util/url';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { ClipboardService } from '../../tool-pallette/services/clipboard-service';
import { ContentContainerBase } from '../base/contents-container-base';
import { ContentsContainerService } from '../services/content-container.service';
import { GraphicalEditorService } from '../../graphical-editor/services/graphical-editor.service';
import { ModelImageService } from '../../graphical-editor/services/model-image.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-specification-container',
    templateUrl: 'test-specification-container.component.html',
    styleUrls: ['test-specification-container.component.css']
})
export class TestSpecificationContainer extends ContentContainerBase<TestSpecification> {

    constructor(dataService: SpecmateDataService,
        navigator: NavigatorService,
        translate: TranslateService,
        modal: ConfirmationModal,
        contentService: ContentsContainerService,
        clipboardService: ClipboardService,
        graphicalEditorService: GraphicalEditorService,
        protected modelImageService: ModelImageService) {
        super(dataService, navigator, translate, modal, clipboardService, graphicalEditorService, modelImageService);

        contentService.onModelDeleted.subscribe(
            () => { this.readContents(); });
    }

    public get parent(): IContainer {
        return this._parent;
    }

    @Input()
    public set parent(parent: IContainer) {
        super.setParent(parent);
    }

    protected condition = (element: IContainer) => Type.is(element, TestSpecification);

    public async createElement(name: string): Promise<TestSpecification> {
        let factory: ElementFactoryBase<TestSpecification> = new TestSpecificationFactory(this.dataService);
        return await factory.create(this.parent, true, Id.uuid, name);
    }

    public async recycle(element: IContainer): Promise<void> {
        const contents = await this.dataService.readContents(element.url, true);
        const numberOfChlidren = contents.filter(element => Type.is(element, TestCase)).length;
        const additionalMessage = this.translate.instant('attentionNumberOfTestCasesWillBeDeleted', { num: numberOfChlidren });
        const baseMessage = this.translate.instant('doYouReallyWantToDelete', { name: element.name });
        await super.recycle(element, baseMessage + ' ' + additionalMessage);
        await this.dataService.readContents(Url.parent(element.url), false);
    }

    protected async readContents(): Promise<void> {
        const contents = await this.dataService
            .performQuery(this.parent.url, 'listRecursive', { class: TestSpecification.className });
        this.contents = Sort.sortArray(contents);
    }
}
