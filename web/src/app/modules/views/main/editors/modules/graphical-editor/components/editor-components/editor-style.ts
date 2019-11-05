/* tslint:disable:max-line-length */

import { mxgraph } from 'mxgraph'; // Typings only - no code!

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
    mxBasePath: 'mxgraph'
});


export type Style = { [key: string]: string; };

/**
 * Stylesheet for the graphical editor.
 */
export class EditorStyle {

    private static createStyle(styleStr: string): Style {
        const style: Style = {};
        const parts = styleStr.split(';')
            .filter(styleStrPart => styleStrPart !== undefined && styleStrPart !== null && styleStrPart.length > 0)
            .map(styleStrPart => styleStrPart.trim())
            .map(stylePart => stylePart.split('='))
            .forEach(keyValue => style[keyValue[0]] = keyValue[1]);
        return style;
    }

    public static readonly VALID_STYLE_NAME = 'VALID';
    public static readonly INVALID_STYLE_NAME = 'INVALID';
    public static readonly VALID_STYLE: Style = {};
    public static readonly INVALID_STYLE: Style = {};

    public static readonly EDGE_HIGHLIGHT_STYLE_NAME = 'EDGE_HIGHLIGHT';
    public static readonly EDGE_DIM_STYLE_NAME = 'EDGE_DIM';
    public static readonly EDGE_HIGHLIGHT_STYLE: Style = {};
    public static readonly EDGE_DIM_STYLE: Style = {};

    public static readonly CAUSE_STYLE_NAME = 'CAUSE';
    public static readonly INNER_STYLE_NAME = 'INNER';
    public static readonly EFFECT_STYLE_NAME = 'EFFECT';
    public static readonly CAUSE_STYLE: Style = {};
    public static readonly INNER_STYLE: Style = {};
    public static readonly EFFECT_STYLE: Style = {};

    public static readonly BASE_CEG_NODE_STYLE = 'BASE_CEG_NODE';
    private static readonly BASE_CEG_NODE_STYLE_STR = 'shape=rectangle;rounded=1;arcSize=10;align=center;perimeter=rectanglePerimeter';
    private static readonly BASE_CEG_NODE_STYLE_OBJ: Style = EditorStyle.createStyle(EditorStyle.BASE_CEG_NODE_STYLE_STR);
    public static readonly BASE_PROCESS_START_STYLE = 'BASE_PROCESS_START_STYLE';
    private static readonly BASE_PROCESS_START_STYLE_STR = 'shape=ellipse;whiteSpace=wrap;html=1;aspect=fixed;align=center;perimeter=ellipsePerimeter;editable=0';
    private static readonly BASE_PROCESS_START_STYLE_OBJ: Style = EditorStyle.createStyle(EditorStyle.BASE_PROCESS_START_STYLE_STR);
    public static readonly BASE_PROCESS_END_STYLE = 'BASE_PROCESS_END_STYLE';
    private static readonly BASE_PROCESS_END_STYLE_STR = 'shape=doubleEllipse;whiteSpace=wrap;html=1;aspect=fixed;align=center;perimeter=ellipsePerimeter;editable=0';
    private static readonly BASE_PROCESS_END_STYLE_OBJ: Style = EditorStyle.createStyle(EditorStyle.BASE_PROCESS_END_STYLE_STR);
    public static readonly BASE_PROCESS_STEP_STYLE = 'BASE_PROCESS_STEP_STYLE';
    private static readonly BASE_PROCESS_STEP_STYLE_STR = 'shape=rectangle;rounded=1;arcSize=10;perimeter=rectanglePerimeter';
    private static readonly BASE_PROCESS_STEP_STYLE_OBJ: Style = EditorStyle.createStyle(EditorStyle.BASE_PROCESS_STEP_STYLE_STR);
    public static readonly BASE_PROCESS_DECISION_STYLE = 'BASE_PROCESS_DECISION_STYLE';
    private static readonly BASE_PROCESS_DECISION_STYLE_STR = 'shape=rhombus;align=center;perimeter=rhombusPerimeter';
    private static readonly BASE_PROCESS_DECISION_STYLE_OBJ: Style = EditorStyle.createStyle(EditorStyle.BASE_PROCESS_DECISION_STYLE_STR);

    public static readonly ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE = 'ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE';
    private static readonly ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE_STR = 'dashed=1';
    private static readonly ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE_OBJ: Style = EditorStyle.createStyle(EditorStyle.ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE_STR);



    private static initStyles() {
        mx.mxConstants.HANDLE_FILLCOLOR = '#99ccff';
        mx.mxConstants.HANDLE_STROKECOLOR = '#0088cf';
        mx.mxConstants.VERTEX_SELECTION_COLOR = '#00a8ff';
        mx.mxConstants.EDGE_SELECTION_COLOR = '#00a8ff';
        mx.mxConstants.DEFAULT_FONTSIZE = 12;

        EditorStyle.VALID_STYLE[mx.mxConstants.STYLE_DASHED] = '0';
        EditorStyle.VALID_STYLE[mx.mxConstants.STYLE_STROKEWIDTH] = '3';
        EditorStyle.VALID_STYLE[mx.mxConstants.STYLE_STROKE_OPACITY] = '100';
        EditorStyle.VALID_STYLE[mx.mxConstants.STYLE_STROKECOLOR] = '#346CB6';

        EditorStyle.INVALID_STYLE[mx.mxConstants.STYLE_DASHED] = '0';
        EditorStyle.INVALID_STYLE[mx.mxConstants.STYLE_STROKEWIDTH] = '3';
        EditorStyle.INVALID_STYLE[mx.mxConstants.STYLE_STROKE_OPACITY] = '100';
        EditorStyle.INVALID_STYLE[mx.mxConstants.STYLE_STROKECOLOR] = '#ff0000';

        EditorStyle.CAUSE_STYLE[mx.mxConstants.STYLE_FILLCOLOR] = '#39C4B8';
        EditorStyle.EFFECT_STYLE[mx.mxConstants.STYLE_FILLCOLOR] = '#f6960d';
        EditorStyle.INNER_STYLE[mx.mxConstants.STYLE_FILLCOLOR] = '#e0e026';

        EditorStyle.EDGE_HIGHLIGHT_STYLE[mx.mxConstants.STYLE_STROKECOLOR] = '#000000';
        EditorStyle.EDGE_HIGHLIGHT_STYLE[mx.mxConstants.STYLE_STROKEWIDTH] = '3';

        EditorStyle.EDGE_DIM_STYLE[mx.mxConstants.STYLE_STROKECOLOR] = '#858585';
        EditorStyle.EDGE_DIM_STYLE[mx.mxConstants.STYLE_STROKEWIDTH] = '2';
    }

    public static initEditorStyles(graph: mxgraph.mxGraph) {
        EditorStyle.initStyles();

        const stylesheet = graph.getStylesheet();

        stylesheet.putCellStyle(EditorStyle.BASE_CEG_NODE_STYLE, EditorStyle.BASE_CEG_NODE_STYLE_OBJ);
        stylesheet.putCellStyle(EditorStyle.BASE_PROCESS_START_STYLE, EditorStyle.BASE_PROCESS_START_STYLE_OBJ);
        stylesheet.putCellStyle(EditorStyle.BASE_PROCESS_END_STYLE, EditorStyle.BASE_PROCESS_END_STYLE_OBJ);
        stylesheet.putCellStyle(EditorStyle.BASE_PROCESS_STEP_STYLE, EditorStyle.BASE_PROCESS_STEP_STYLE_OBJ);
        stylesheet.putCellStyle(EditorStyle.BASE_PROCESS_DECISION_STYLE, EditorStyle.BASE_PROCESS_DECISION_STYLE_OBJ);

        stylesheet.putCellStyle(EditorStyle.ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE, EditorStyle.ADDITIONAL_CEG_CONNECTION_NEGATED_STYLE_OBJ);

        stylesheet.putCellStyle(EditorStyle.VALID_STYLE_NAME, EditorStyle.VALID_STYLE);
        stylesheet.putCellStyle(EditorStyle.INVALID_STYLE_NAME, EditorStyle.INVALID_STYLE);
        stylesheet.putCellStyle(EditorStyle.CAUSE_STYLE_NAME, EditorStyle.CAUSE_STYLE);
        stylesheet.putCellStyle(EditorStyle.EFFECT_STYLE_NAME, EditorStyle.EFFECT_STYLE);
        stylesheet.putCellStyle(EditorStyle.INNER_STYLE_NAME, EditorStyle.INNER_STYLE);

        const vertexStyle = graph.getStylesheet().getDefaultVertexStyle();
        vertexStyle[mx.mxConstants.STYLE_STROKECOLOR] = '#000000';
        vertexStyle[mx.mxConstants.STYLE_DASHED] = '1';
        vertexStyle[mx.mxConstants.STYLE_DASH_PATTERN] = '4';

        stylesheet.putCellStyle(EditorStyle.EDGE_DIM_STYLE_NAME, EditorStyle.EDGE_DIM_STYLE);
        stylesheet.putCellStyle(EditorStyle.EDGE_HIGHLIGHT_STYLE_NAME, EditorStyle.EDGE_HIGHLIGHT_STYLE);

        const edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
        edgeStyle[mx.mxConstants.STYLE_STROKECOLOR] = EditorStyle.EDGE_DIM_STYLE[mx.mxConstants.STYLE_STROKECOLOR];
        edgeStyle[mx.mxConstants.STYLE_STROKEWIDTH] = EditorStyle.EDGE_DIM_STYLE[mx.mxConstants.STYLE_STROKEWIDTH];
    }
}
