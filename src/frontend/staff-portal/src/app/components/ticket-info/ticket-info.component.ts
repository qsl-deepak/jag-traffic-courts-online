import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute, Router } from '@angular/router';
import { LoggerService } from '@core/services/logger.service';
import { UtilsService } from '@core/services/utils.service';
import { FormControlValidators } from '@core/validators/form-control.validators';

@Component({
  selector: 'app-ticket-info',
  templateUrl: './ticket-info.component.html',
  styleUrls: ['./ticket-info.component.scss'],
})
export class TicketInfoComponent implements OnInit {
  @Input() public ticketInfo: any; // FIXME: this should be a specific type, not "any"
  @Output() public backTicketList: EventEmitter<MatStepper> = new EventEmitter();
  public isMobile: boolean;
  public previousButtonIcon = 'keyboard_arrow_left';
  public previousButtonKey = 'stepper.backReview';
  public saveButtonKey = 'stepper.next';

  public maxDateOfBirth: Date;
  public form: FormGroup;
  public todayDate: Date = new Date();
  // public maxDateOfBirth: Date;
  private MINIMUM_AGE = 18;

  /**
   * @description
   * Whether to show the address line fields.
   */
   public showManualButton: boolean;
   public showAddressFields: boolean;
   public disableForm:boolean = true 
   public collapseObj: any = {
    ticketInformation: true
  }
  constructor(
    protected route: ActivatedRoute,
    protected router: Router,
    protected formBuilder: FormBuilder,
    private utilsService: UtilsService,
    private logger: LoggerService
  ) {
    const today = new Date();
    this.maxDateOfBirth = new Date();
    this.maxDateOfBirth.setFullYear(today.getFullYear() - this.MINIMUM_AGE);
    this.isMobile = this.utilsService.isMobile();
    this.showManualButton = true;
    this.showAddressFields = true;
  }

  public ngOnInit() {
    this.form = this.formBuilder.group({
      violationTicketNumber: [null, [Validators.required]],
      phoneNumber: [null],
      emailAddress: [null],
      violationDate: [null, [Validators.required]],
      violationTime: [null, [Validators.required]],
      surname: [null, [Validators.required]],
      givenNames: [null, [Validators.required]],
      mailingAddress: [null],
      country: [null],
      birthdate: [null], // Optional
      gender: [null, [Validators.required]],
      address: [null, [Validators.required]],
      city: [null, [Validators.required]],
      province: [null, [Validators.required]],
      postalCode: [null, [Validators.required]],
      driverLicenseNumber: [null, [Validators.required]],
      driverLicenseProvince: [null, [Validators.required]],
      count1Charge: [
        null,
        [Validators.required],
      ],
      count1FineAmount: [
        null,
        [Validators.required, FormControlValidators.currency],
      ],
      count2Charge: [
        null,
        [Validators.required],
      ],
      count2FineAmount: [
        null,
        [Validators.required, FormControlValidators.currency],
      ],
      count3Charge: [
        null,
        [Validators.required],
      ],
      count3FineAmount: [
        null,
        [Validators.required, FormControlValidators.currency],
      ],
      courtHearingLocation: [null, [Validators.required]],
      detachmentLocation: [null, [Validators.required]],
      _chargeCount: [1],
      _amountOwing: [null],
    });
console.log(">",this.form.get('violationTicketNumber'));
    this.form.patchValue({
      violationTicketNumber: "",
      violationDate: "",
      violationTime: "",
      surname: this.ticketInfo.Surname,
      givenNames: this.ticketInfo.GivenName,
      birthdate: [null], // Optional
      gender: "",
      address: "",
      city: "",
      province: "",
      postalCode: "",
      driverLicenseNumber: "",
      driverLicenseProvince: "",
    });
  }

  public onBack() {
    this.backTicketList.emit();
  }

  public onSubmit(): void {
  }

  public handleCollapse(name: string) {
    this.collapseObj[name]= !this.collapseObj[name]
  }
  
  public showManualAddress(): void {
    this.showAddressFields = true;
  }

  public editForm(): void {
    this.disableForm = false;
  }

  public get violationTicketNumber(): FormControl {
    return this.form.get('violationTicketNumber') as FormControl;
  }

  public get violationDate(): FormControl {
    return this.form.get('violationDate') as FormControl;
  }

  
}
