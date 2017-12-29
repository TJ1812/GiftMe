import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminComponent } from './admin/admin.component';
import { AddItemComponent } from './add-item/add-item.component';
import { MyRegistryComponent } from './my-regi/my-regi.component';
import { SharedRegistryComponent } from './shared-regi/shared-regi.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AllItemsComponent } from './all-items/all-items.component';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { ProfileComponent } from './profile/profile.component';
import { UnauthAccessComponenet } from './unauth-access/unauth-access.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';


const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'myRegi', component: MyRegistryComponent },
    { path: 'sharedRegi', component: SharedRegistryComponent},
    { path: 'addItem', component: AddItemComponent},
    { path: 'dashboard', component: DashboardComponent },
    { path: 'items', component: AllItemsComponent },
    { path: 'item/:id', component: ItemDetailComponent },
    { path: 'profile', component: ProfileComponent },
    { path: 'unauth', component: UnauthAccessComponenet },
    { path: 'NotFound', component: PageNotFoundComponent },
    { path: '**', redirectTo: '/NotFound', pathMatch: 'full' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {

}  