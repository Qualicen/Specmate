import { SpecmateDataService } from 'src/app/modules/data/modules/data-service/services/specmate-data.service';
import { IContainer } from '../../model/IContainer';
import { Process } from '../../model/Process';
import { ProcessEnd } from '../../model/ProcessEnd';
import { Type } from '../../util/type';
import { ElementValidatorBase } from '../element-validator-base';
import { ValidationMessage } from '../validation-message';
import { ValidationResult } from '../validation-result';
import { Validator } from '../validator-decorator';

@Validator(Process)
export class EndNodeNoOutgoingConnectionValidator extends ElementValidatorBase<Process> {
    public async validate(element: Process, contents: IContainer[], dataService: SpecmateDataService): Promise<ValidationResult> {
        const invalidNodes: IContainer[] = contents
            .filter((element: IContainer) => Type.is(element, ProcessEnd))
            .filter((element: ProcessEnd) => element.outgoingConnections && element.outgoingConnections.length > 0);
        if (invalidNodes.length > 0) {
            return new ValidationResult(ValidationMessage.ERROR_PROCESS_END_OUTGOING_CONNECTION, false, invalidNodes);
        }
        return ValidationResult.VALID;
    }
}
