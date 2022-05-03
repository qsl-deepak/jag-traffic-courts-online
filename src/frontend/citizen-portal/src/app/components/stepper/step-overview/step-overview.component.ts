import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfigService } from '@config/config.service';
import { FormUtilsService } from '@core/services/form-utils.service';
import { LoggerService } from '@core/services/logger.service';
import { ToastService } from '@core/services/toast.service';
import { UtilsService } from '@core/services/utils.service';
import { TranslateService } from '@ngx-translate/core';
import { TicketDisputeView } from '@shared/models/ticketDisputeView.model';
import { ViolationTicket } from 'app/api';
import { BaseDisputeFormPage } from 'app/components/classes/BaseDisputeFormPage';
import { DisputeFormStateService } from 'app/services/dispute-form-state.service';
import { DisputeResourceService } from 'app/services/dispute-resource.service';
import { DisputeService } from 'app/services/dispute.service';

@Component({
  selector: 'app-step-overview',
  templateUrl: './step-overview.component.html',
  styleUrls: ['./step-overview.component.scss'],
})
export class StepOverviewComponent implements OnInit {
  @Input() public overviewTicket: any;
  @Input() public stepper: MatStepper;
  @Input() public countDataList: any;
  @Output() public stepSave: EventEmitter < MatStepper > = new EventEmitter();

  public form: FormGroup;
  public ticket: ViolationTicket;
  public defaultLanguage: string;
  public previousButtonIcon = 'keyboard_arrow_left';
  public previousButtonKey = 'stepper.back';
  public saveButtonKey = 'stepper.submit';
  public declared = false;

  constructor(
    protected route: ActivatedRoute,
    protected router: Router,
    protected formBuilder: FormBuilder,
    // protected disputeService: DisputeService,
    // protected disputeResource: DisputeResourceService,
    // protected disputeFormStateService: DisputeFormStateService,
    private formUtilsService: FormUtilsService,
    private utilsService: UtilsService,
    private logger: LoggerService,
    private toastService: ToastService,
    private configService: ConfigService,
    private translateService: TranslateService
  ) {
    this.defaultLanguage = this.translateService.getDefaultLang();
  }

  public ngOnInit() {
  }

  public onBack() {
    this.stepper.previous();
  }

  public onSubmit(): void {
    const validForm = this.formUtilsService.checkValidity(this.form);
    const errors = this.formUtilsService.getFormErrors(this.form);

    this.logger.log('validity', validForm);
    this.logger.log('errors', errors);
    this.logger.log('form.value', this.form.value);

    // const validForms = this.disputeFormStateService.isValid;
    // this.logger.log('disputeFormStateService.isValid', validForms);

    // if(validForm) {
    //   if (validForms) {
    //     this.stepSave.emit(this.stepper);
    //     return;
    //   } else {
    //     this.toastService.openErrorToast(
    //       this.configService.dispute_validation_error
    //     );
    //   }
    // }
    this.utilsService.scrollToErrorSection();
  }
}
