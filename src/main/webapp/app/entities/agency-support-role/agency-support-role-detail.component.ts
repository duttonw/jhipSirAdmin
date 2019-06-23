import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';

@Component({
  selector: 'jhi-agency-support-role-detail',
  templateUrl: './agency-support-role-detail.component.html'
})
export class AgencySupportRoleDetailComponent implements OnInit {
  agencySupportRole: IAgencySupportRole;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agencySupportRole }) => {
      this.agencySupportRole = agencySupportRole;
    });
  }

  previousState() {
    window.history.back();
  }
}
