/* tslint:disable */
	import './support/gentypes';
	import { Proxy } from './support/proxy';


	export class ParameterAssignment  {

		___nsuri: string = "http://specmate.com/20200921/model/testspecification";
		public url: string;
		public className: string = "ParameterAssignment";
		public static className: string = "ParameterAssignment";
		// Attributes
		public id: EString;
		public name: EString;
		public description: EString;
		public recycled: EBoolean;
		public hasRecycledChildren: EBoolean;
		public value: EString;
		public condition: EString;

		// References
		public parameter: Proxy;

		// Containment


	}

