/* tslint:disable */
	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class ProcessStep  {

		___nsuri: string = "http://specmate.com/20200921/model/processes";
		public url: string;
		public className: string = "ProcessStep";
		public static className: string = "ProcessStep";
		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public recycled: EBoolean;
		public hasRecycledChildren: EBoolean;
		public x: EDouble;
		public y: EDouble;
		public width: EDouble;
		public height: EDouble;
		public expectedOutcome: EString;

		// References
		
		public tracesTo: Proxy[];
		public tracesFrom: Proxy[];
		public outgoingConnections: Proxy[];
		public incomingConnections: Proxy[];

		// Containment


	}

