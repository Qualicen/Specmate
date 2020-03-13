import { ElementFactoryBase } from '../../../../../../../factory/element-factory-base';
import { IContainer } from '../../../../../../../model/IContainer';
import { IModelNode } from '../../../../../../../model/IModelNode';
import { SpecmateDataService } from '../../../../../../data/modules/data-service/services/specmate-data.service';
import { SelectedElementService } from '../../../../../side/modules/selected-element/services/selected-element.service';
import { Coords } from '../../graphical-editor/util/coords';
import { CreateToolBase } from './create-tool-base';

export abstract class CreateNodeToolBase<T extends IModelNode> extends CreateToolBase {

    public color = 'primary';
    public isVertexTool = true;
    public isHidden = false;

    public coords: { x: number, y: number };
    public name: string;

    public async perform(): Promise<T> {
        if (this.coords === undefined) {
            throw new Error('Coords undefined');
        }
        return await this.createNewNodeAtCoords();
    }

    private async createNewNodeAtCoords(): Promise<T> {
        if (this.name === undefined || this.coords === undefined) {
            throw new Error('Necessary data undefined.');
        }
        const factory = this.getElementFactory(this.coords);
        const node = await factory.create(this.parent, false, undefined, this.name);
        this.selectedElementService.select(node);
        return node;
    }

    protected abstract getElementFactory(coords: { x: number, y: number }): ElementFactoryBase<T>;
}
