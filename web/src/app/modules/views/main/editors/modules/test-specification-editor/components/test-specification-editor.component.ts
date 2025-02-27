import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DragulaService } from 'ng2-dragula';
import { TestCaseFactory } from '../../../../../../../factory/test-case-factory';
import { TestInputParameterFactory } from '../../../../../../../factory/test-input-parameter-factory';
import { TestOutputParameterFactory } from '../../../../../../../factory/test-output-parameter-factory';
import { TestParameterFactory } from '../../../../../../../factory/test-parameter-factory';
import { IContainer } from '../../../../../../../model/IContainer';
import { TestCase } from '../../../../../../../model/TestCase';
import { TestParameter } from '../../../../../../../model/TestParameter';
import { TestSpecification } from '../../../../../../../model/TestSpecification';
import { Sort } from '../../../../../../../util/sort';
import { Type } from '../../../../../../../util/type';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { NavigatorService } from '../../../../../../navigation/modules/navigator/services/navigator.service';
import { ConfirmationModal } from '../../../../../../notification/modules/modals/services/confirmation-modal.service';
import { DraggableSupportingViewBase } from '../../../base/draggable-supporting-view-base';
import { UndoService } from 'src/app/modules/actions/modules/common-controls/services/undo.service';

@Component({
    moduleId: module.id.toString(),
    selector: 'test-specification-editor',
    templateUrl: 'test-specification-editor.component.html',
    styleUrls: ['test-specification-editor.component.css']
})
export class TestSpecificationEditor extends DraggableSupportingViewBase {

    /** The test specification to be shown */
    public testSpecification: TestSpecification;

    public relevantElements: TestCase[];

    /** Constructor */
    constructor(
        dataService: SpecmateDataService,
        navigator: NavigatorService,
        route: ActivatedRoute,
        modal: ConfirmationModal,
        dragulaService: DragulaService,
        private undoService: UndoService,
        translate: TranslateService
    ) {
        super(dataService, navigator, route, modal, dragulaService, translate);
        this.undoService.undoPressed.subscribe(() => {
            this.updateContents();
        });

    }

    public onElementResolved(element: IContainer): Promise<void> {
        return super.onElementResolved(element)
            .then(() => Type.is(element, TestSpecification) ? Promise.resolve() : Promise.reject('Not a test specification'))
            .then(() => this.testSpecification = element as TestSpecification)
            .then(() => Promise.resolve());
    }

    public async onContentsRead(elements: IContainer[]): Promise<void> {
        this.relevantElements = Sort.sortArrayBy(this.contents.filter(element => this.isTestCase(element)), 'position') as TestCase[];
    }

    /** getter for the input parameters */
    public get inputParameters(): TestParameter[] {
        return this.testParameters.filter((testParameter: TestParameter) => testParameter.type === 'INPUT');
    }

    /** getter for the output parameters */
    public get outputParameters(): TestParameter[] {
        return this.testParameters.filter((testParameter: TestParameter) => testParameter.type === 'OUTPUT');
    }

    /** getter for all parameters */
    private get testParameters(): TestParameter[] {
        return this.contents
            .filter((element: IContainer) => Type.is(element, TestParameter))
            .map((element: IContainer) => element as TestParameter);
    }

    /** Adds a new test case (row) */
    public addTestCaseRow(): void {
        let factory: TestCaseFactory = new TestCaseFactory(this.dataService, true);
        factory.create(this.testSpecification, false).then(() => this.updateContents());
    }

    /** Adds a new input column */
    public addInputColumn(): void {
        let factory: TestParameterFactory = new TestInputParameterFactory(this.dataService);
        factory.create(this.testSpecification, false).then(() => this.updateContents());
    }

    /** Adds a new output column  */
    public addOutputColumn(): void {
        let factory: TestParameterFactory = new TestOutputParameterFactory(this.dataService);
        factory.create(this.testSpecification, false).then(() => this.updateContents());
    }

    /** Returns true if the element is a TestCase - Important in UI. */
    public isTestCase(element: IContainer): boolean {
        return Type.is(element, TestCase);
    }

    /** Return true if all user inputs are valid  */
    protected get isValid(): boolean {
        return true;
    }
}
