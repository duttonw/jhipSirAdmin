import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';

@Component({
  selector: 'jhi-agency-support-role-context-type-detail',
  templateUrl: './agency-support-role-context-type-detail.component.html'
})
export class AgencySupportRoleContextTypeDetailComponent implements OnInit {
  agencySupportRoleContextType: IAgencySupportRoleContextType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agencySupportRoleContextType }) => {
      this.agencySupportRoleContextType = agencySupportRoleContextType;
    });
  }

  previousState() {
    window.history.back();
  }
}
