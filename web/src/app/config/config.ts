export class Config {

    public static URL_BASE = 'services/rest/';
    public static URL_CONFIG = '/config';
    public static URL_BATCH = '/batch';
    public static URL_CONTENTS = '/list';
    public static URL_ELEMENT = '/details';
    public static URL_DELETE = '/delete';

    // If you change this, you also need to change the Jetty Redirects (jetty-redirects.xml)
    public static VIEW_URL_PREFIX = '-/';

    public static LOGIN_URL = Config.VIEW_URL_PREFIX + 'login';
    public static WELCOME_URL_PART = 'welcome';
    public static WELCOME_URL = Config.VIEW_URL_PREFIX + Config.WELCOME_URL_PART;

    // For all of these languages, we need to
    // - have a <lang>.json file in assets/i18n
    // - update it with the translation extractor in package.json (run 'npm run extract')
    public static LANGUAGES = [
        { code: 'de', name: 'Deutsch' },
        { code: 'gb', name: 'English' }
    ];
    public static DEFAULT_LANGUAGE = Config.LANGUAGES[0];
    public static LANGUAGE_CHOOSER_ENABLED = true;
    public static USE_BROWSER_LANGUAGE = false;

    public static CONNECTIVITY_CHECK_DELAY = 10000;
    public static NUM_HTTP_RETRIES = 10;
    public static HTTP_RETRY_DELAY = 500;
    public static HTTP_RETRY_ERRORS = [503, 404, 403, 401];

    public static ACTIVATE_DEFAUTL_TOOL_ON_SUCCESS = false;

    public static LOADING_MODAL_DELAY = 300;
    public static LOADING_DEBOUNCE_DELAY = 10;

    public static LOG_START_MESSAGE = 'Specmate Started';
    public static LOG_LENGTH = 100;
    public static LOG_DEFAULT_COLOR = 'muted';
    public static LOG_DEFAULT_ICON = 'comment';
    public static LOG_INITIALLY_SHOWN = false;

    public static MERGE_CONFLICT = 'Tried to merge commands with conflicting operations.';
    public static TRIED_TO_ADD_EXISTING_ELEMENT = 'Tried to add an existing element to parent! ';

    public static SEARCH_MINIMUM_LENGTH = 2;

    // Editor settings
    public static GRAPHICAL_EDITOR_WIDTH = 1000;
    public static GRAPHICAL_EDITOR_HEIGHT: number = (isNaN(window.innerHeight) ? window['clientHeight'] : window.innerHeight) * 0.75;
    public static GRAPHICAL_EDITOR_PADDING_HORIZONTAL = 300;
    public static GRAPHICAL_EDITOR_PADDING_VERTICAL = 300;
    public static GRAPHICAL_EDITOR_VISIBILITY_HORIZONTAL = 100;
    public static GRAPHICAL_EDITOR_VISIBILITY_VERTICAL = 100;

    public static GRAPHICAL_EDITOR_GRID_SPACE = 15;
    public static GRAPHICAL_EDITOR_ZOOM_STEP = 0.1;
    public static GRAPHICAL_EDITOR_ZOOM_MAX = 5;

    public static CEG_NODE_WIDTH = 180;
    public static CEG_NODE_HEIGHT = 90;
    public static CEG_NODE_ARC_DIST: number = 17 +
        Math.sqrt((Config.CEG_NODE_WIDTH / 2.0) * (Config.CEG_NODE_WIDTH / 2.0) +
            (Config.CEG_NODE_HEIGHT / 2.0) * (Config.CEG_NODE_HEIGHT / 2.0));

    public static CEG_NEW_MODEL_NAME = 'New Model';
    public static CEG_NEW_MODEL_DESCRIPTION = '';

    public static EMPTY_NODE_NAME = 'Empty Node';
    public static CEG_NEW_NODE_NAME = 'New Node';
    public static CEG_NEW_NODE_DESCRIPTION = '';
    public static CEG_NODE_NEW_TYPE = 'AND';
    public static CEG_NODE_NEW_VARIABLE = 'variable';
    public static CEG_NODE_NEW_CONDITION = 'is present';

    public static CEG_NEW_CONNECTION_NAME = 'New Connection';
    public static CEG_NEW_CONNECTION_DESCRIPTION = '';

    public static PROCESS_DECISION_NODE_DIM = 80;
    public static PROCESS_START_END_NODE_RADIUS = 25;
    public static PROCESS_NEW_PROCESS_NAME = 'New Process';
    public static PROCESS_NEW_PROCESS_DESCRIPTION = '';
    public static PROCESS_NEW_STEP_NAME = 'New Activity';
    public static PROCESS_NEW_STEP_DESCRIPTION = '';
    public static PROCESS_NEW_DECISION_NAME = 'Decision';
    public static PROCESS_NEW_DECISION_DESCRIPTION = '';
    public static PROCESS_NEW_START_NAME = '';
    public static PROCESS_NEW_START_DESCRIPTION = '';
    public static PROCESS_NEW_END_NAME = '';
    public static PROCESS_NEW_END_DESCRIPTION = '';
    public static PROCESS_NEW_CONNECTION_NAME = 'New Connection';
    public static PROCESS_NEW_CONNECTION_DESCRIPTION = '';

    public static TESTSPEC_NAME = 'New Test Specification';
    public static TESTSPEC_DESCRIPTION = '';
    public static TESTSPEC_DESCRIPTION_TRUNC_LENGTH = 120;

    public static TESTPARAMETER_NAME = 'New Test Parameter';

    public static TESTPARAMETERASSIGNMENT_NAME = 'New Test Parameter Assignment';
    public static TESTPARAMETERASSIGNMENT_DEFAULT_CONDITION = '';
    public static TESTPARAMETERASSIGNMENT_DEFAULT_VALUE = '';

    public static TESTCASE_NAME = 'New Test Case';

    public static TESTPROCEDURE_NAME = 'New Test Procedure';
    public static TESTPROCEDURE_DESCRIPTION = '';

    public static TESTSTEP_NAME = 'New Test Step';
    public static TESTSTEP_ACTION = '';
    public static TESTSTEP_EXPECTED_OUTCOME = '';

    public static FOLDER_NEW_NAME = 'New Folder';
    public static FOLDER_NEW_DESCRIPTION = '';

    public static ERROR_UNCONNECTED_NODE = 'Unconnected node(s) in model.';
    public static ERROR_DUPLICATE_IO_VARIABLE = 'Variable(s) appears as cause and effect.';
    public static ERROR_DUPLICATE_NODE = 'Duplicate node(s).';
    public static ERROR_EMPTY_MODEL = 'Model empty.';
    public static ERROR_NOT_ONE_START_NODE = 'Not exactly one start node.';
    public static ERROR_NO_END_NODE = 'No end node.';
    public static ERROR_NODE_WITHOUT_INCOMING = 'Node(s) without incoming connection.';
    public static ERROR_NODE_WITHOUT_OUTGOING = 'Node(s) without outgoing connection.';
    public static ERROR_MISSING_CONDITION = 'Missing condition(s).';
    public static ERROR_MISSING_FIELDS = 'Missing fields: {{fields}}';
    public static ERROR_NO_STEPS = 'No steps in model.';
    public static ERROR_PROCESS_END_OUTGOING_CONNECTION = 'End node(s) with outgoing connection.';
    public static ERROR_PROCESS_START_INCOMING_CONNECTION = 'Start node(s) with incoming connection.';
    public static ERROR_PROCESS_NODE_MULTIPLE_OUTGOING_CONNECTIONS = 'Non-decision-node(s) with multiple outgoing connections.';
    public static ERROR_PROCESS_DECISION_WITH_ONE_OR_LESS_OUTGOING_CONNECTIONS = 'Decision node(s) not at least two outgoing connections.';
    public static ERROR_INVALID_NAME = 'Invalid character in name.';
    // Number of elements to load at each step in the project explorer
    public static ELEMENT_CHUNK_SIZE = 100;


    public static readonly CEG_LAYOUT_CLEARANCE_X = 45;
    public static readonly CEG_LAYOUT_CLEARANCE_Y = 45;

    public static readonly SUPPORTED_BROWSERS = [
        { browser: 'ie', minVersion: 12 },
        { browser: 'chrome', minVersion: 48 },
        { browser: 'firefox', minVersion: 45 },
        { browser: 'edge', minVersion: 15 },
        { browser: 'edge-chromium', minVersion: 79 },
        { browser: 'safari', minVersion: 10 },
        { browser: 'opera', minVersion: 30 }];

}

