import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { SpecmateDataService } from '../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../navigation/modules/navigator/services/navigator.service';
import { ViewControllerService } from '../../../../views/controller/modules/view-controller/services/view-controller.service';
import { ModalBase } from '../base/modal-base';
import { Dialogtype } from '../modal-dialog-type';
import { ModelImageService } from 'src/app/modules/views/main/editors/modules/graphical-editor/services/model-image.service';
import { SpecmateType } from 'src/app/util/specmate-type';

@Component({
    moduleId: module.id.toString(),
    selector: 'typed-modal-content',
    templateUrl: 'typed-modal-content.component.html',
    styleUrls: ['typed-modal-content.component.css']
})

export class TypedModalContent extends ModalBase {
    @Input()
    public _options: Dialogtype;

    public set options(newOptions: Dialogtype) {
        this._options = newOptions;
    }

    constructor(public activeModal: NgbActiveModal,
        private viewControllerService: ViewControllerService,
        private dataService: SpecmateDataService,
        public navigator: NavigatorService,
        private translate: TranslateService,
        private modelImageService: ModelImageService) {
        super(activeModal);
        this._options = Dialogtype.DEFAULT;
    }


    public async save() {
        if (this.isSaveShown) {
            let element = this.navigator.currentElement;
            if (SpecmateType.isModel(element)) {
                await this.modelImageService.createModelImage(element);
            }
            this.dataService.commit(this.translate.instant('save'))
                .then(this.activeModal.close);
        }
    }

    public openLog(): void {
        this.activeModal.dismiss();
        this.viewControllerService.showLoggingOutput();
    }

    public navigateForward(): void {
        this.navigator.forward();
        this.activeModal.dismiss();
    }

    public navigateBack(): void {
        this.navigator.back();
        this.activeModal.dismiss();
    }

    public get isForwardShown(): boolean {
        return this.navigator.hasNext && this._options.hasForeward;
    }

    public get isBackShown(): boolean {
        return this.navigator.hasPrevious && this._options.hasBackward;
    }

    public get isSaveShown(): boolean {
        return this.dataService.hasCommits && this._options.hasSave;
    }

    public get message(): string {
        return this._options.message;
    }

    public get title(): string {
        return this._options.title;
    }

    public get isAcceptShown(): boolean {
        return this._options.hasAccept;
    }

    public get isDismissShown(): boolean {
        return this._options.hasDismiss;
    }

    public get isShowLogShown(): boolean {
        return this._options.hasShowLog;
    }
}
