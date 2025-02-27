import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { CEGNode } from 'src/app/model/CEGNode';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'effect-selector',
    templateUrl: './effect-selector.component.html',
    styleUrls: ['./effect-selector.component.css']
})
export class EffectSelectorComponent implements OnInit {
    @Input()
    effects: CEGNode[];
    _effect: CEGNode = null;

    @Output()
    private selectedEffect = new EventEmitter<CEGNode>();

    set effect(effect: CEGNode) {
        if (effect === undefined) {
            this._effect = null;
        } else {
            this._effect = effect;
        }
        this.selectedEffect.emit(this._effect);
    }

    selectEffect(node: CEGNode) {
        this.effect = node;
    }

    get effect(): CEGNode {
        return this._effect;
    }

    constructor(private translate: TranslateService) { }

    ngOnInit(): void { }

    get buttonLabel(): string {
        if (this._effect === null) {
            return this.translate.instant('linkingDialog.selectEffect');
        }
        return this.getNodeLabel(this._effect);
    }

    get hasEffects(): boolean {
        return this.effects !== null && this.effects.length > 0;
    }

    get hasSelectedEffect(): boolean {
        return this.effect !== undefined && this.effect !== null;
    }

    getNodeLabel(node: CEGNode): string {
        const condition = this.translate.instant('Condition');
        const variable = this.translate.instant('Variable');
        return variable + ': ' + node.variable + ' ' + condition + ': ' + node.condition;
    }
}
