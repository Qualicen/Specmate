import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfigServiceModule } from 'src/app/modules/config/modules/config-service/config-service.module';
import { IconsModule } from '../../../common/modules/icons/icons.module';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { NavigatorModule } from '../navigator/navigator.module';
import { ElementTree } from './components/element-tree.component';
import { ProjectExplorer } from './components/project-explorer.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    SpecmateSharedModule,
    ConfigServiceModule,
    IconsModule,
    NgbModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ProjectExplorer,
    ElementTree
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ProjectExplorer
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ProjectExplorerModule { }
