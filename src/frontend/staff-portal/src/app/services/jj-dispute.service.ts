import { ConfigService } from '@config/config.service';
import { LoggerService } from '@core/services/logger.service';
import { ToastService } from '@core/services/toast.service';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { JJService, JJDispute, Dispute, DisputeStatus, DisputedCount, LegalRepresentation, ViolationTicket } from 'app/api';

@Injectable({
  providedIn: 'root',
})
export class JJDisputeService {
  private _JJDisputes: BehaviorSubject<JJDispute[]> = new BehaviorSubject<JJDispute[]>(null);
  private _JJDispute: BehaviorSubject<JJDispute> = new BehaviorSubject<JJDispute>(null);
  public jjDisputeStatusesSorted: string[] = ['NEW', 'REVIEW', 'IN_PROGRESS', 'COMPLETED'];

  constructor(
    private toastService: ToastService,
    private logger: LoggerService,
    private configService: ConfigService,
    private jjApiService: JJService,
  ) {
  }

  /**
     * Get the JJ disputes from RSI
     *
     * @param none
     */
  public getJJDisputes(): Observable<JJDispute[]> {
    return this.jjApiService.apiJjDisputesGet()
      .pipe(
        map((response: JJDispute[]) => {
          this.logger.info('jj-DisputeService::getJJDisputes', response);
          this._JJDisputes.next(response);
          return response;
        }),
        catchError((error: any) => {
          this.toastService.openErrorToast(this.configService.dispute_error);
          this.logger.error(
            'jj-DisputeService::getJJDisputes error has occurred: ',
            error
          );
          throw error;
        })
      );
  }

  /**
     * Put the JJ dispute to RSI by Id.
     *
     * @param ticketNumber, jjDispute
     */
   public putJJDispute(ticketNumber: string, jjDispute: JJDispute): Observable<JJDispute> {

    return this.jjApiService.apiJjTicketNumberPut(ticketNumber, jjDispute)
      .pipe(
        map((response: any) => {
          this.logger.info('jj-DisputeService::putJJDispute', response)
          return response ? response : null
        }),
        catchError((error: any) => {
          var errorMsg = error.error.detail != null ? error.error.detail : this.configService.dispute_error;
          this.toastService.openErrorToast(errorMsg);
          this.toastService.openErrorToast(this.configService.dispute_error);
          this.logger.error(
            'jj-DisputeService::putJJDispute error has occurred: ',
            error
          );
          throw error;
        })
      );
  }

  public get JJDisputes$(): Observable<JJDispute[]> {
    return this._JJDisputes.asObservable();
  }

  public get JJDisputes(): JJDispute[] {
    return this._JJDisputes.value;
  }

  /**
   * Get the dispute from RSI by Id.
   *
   * @param disputeId
   */
  public getJJDispute(disputeId: string): Observable<JJDispute> {
    return this.jjApiService.apiJjJJDisputeIdGet(disputeId)
      .pipe(
        map((response: JJDispute) => {
          this.logger.info('jj-DisputeService::getJJDispute', response)
          return response ? response : null
        }),
        catchError((error: any) => {
          var errorMsg = error.error.detail != null ? error.error.detail : this.configService.dispute_error;
          this.toastService.openErrorToast(errorMsg);
          this.logger.error(
            'jj-DisputeService::getJJDispute error has occurred: ',
            error
          );
          throw error;
        })
      );
  }

  public get JJDispute$(): Observable<JJDispute> {
    return this._JJDispute.asObservable();
  }

  public get JJDispute(): JJDispute {
    return this._JJDispute.value;
  }

  public addDays(initialDate: string, numDays: number): Date {
    var futureDate = new Date(initialDate);
    futureDate.setDate(futureDate.getDate() + numDays);
    return futureDate;
  }
}