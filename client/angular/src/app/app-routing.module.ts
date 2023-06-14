import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MarathonComponent } from './marathon/marathon.component';
import { RunnersComponent } from './runners/runners.component';

const routes: Routes = [
    { path: 'marathon', component: MarathonComponent },
    { path: 'runners', component: RunnersComponent },
    { path: '', redirectTo: '/marathon', pathMatch: 'full' }, // redirects from root to /marathon
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
