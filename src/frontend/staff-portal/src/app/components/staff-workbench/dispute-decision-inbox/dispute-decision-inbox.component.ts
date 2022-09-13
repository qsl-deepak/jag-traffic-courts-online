import { Component, OnInit, ViewChild, AfterViewInit, Output, EventEmitter, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { JJDisputeService } from 'app/services/jj-dispute.service';
import { JJDispute } from '../../../api/model/jJDispute.model';
import { LoggerService } from '@core/services/logger.service';
import { Subscription } from 'rxjs';
import { CourthouseConfig } from '@config/config.model';
import { JJDisputeStatus } from 'app/api';
import { MockConfigService } from 'tests/mocks/mock-config.service';

@Component({
  selector: 'app-dispute-decision-inbox',
  templateUrl: './dispute-decision-inbox.component.html',
  styleUrls: ['./dispute-decision-inbox.component.scss'],
})
export class DisputeDecisionInboxComponent implements OnInit, AfterViewInit {
  @Output() public jjDisputeInfo: EventEmitter<JJDispute> = new EventEmitter();
  @Input() public IDIR: string;

  busy: Subscription;
  public courtLocations: CourthouseConfig[];
  currentTeam: string = "All";
  data = [] as JJDisputeView[];
  dataSource = new MatTableDataSource();
  @ViewChild(MatSort) sort = new MatSort();
  displayedColumns: string[] = [
    "ticketNumber",
    "jjDecisionDate",
    "jjAssignedTo",
    "violationDate",
    "disputantName",
    "courthouseLocation",
    "vtcAssignedTo"
  ];

  constructor(
    private logger: LoggerService,
    public jjDisputeService: JJDisputeService,
    public mockConfigService: MockConfigService,
  ) {
    if (this.mockConfigService.courtLocations) {
      this.courtLocations = this.mockConfigService.courtLocations;
    }
    this.jjDisputeService.refreshDisputes.subscribe(x => {this.getAll();})
  }

  filterByTeam(team: string) {
    let teamCourthouses = this.courtLocations.filter(x => (x.jjTeam === team || team === "All"));
    this.dataSource.data = this.data.filter(x => teamCourthouses.filter(y => y.name === x.courthouseLocation).length > 0);
  }

  public ngOnInit() {
    this.getAll();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  backWorkbench(element) {
    this.jjDisputeInfo.emit(element);
  }

  getAll(): void {
    this.logger.log('JJDisputeDecisionInboxComponent::getAllDisputes');

    this.jjDisputeService.getJJDisputes().subscribe((response: JJDisputeView[]) => {
      // filter jj disputes only show those in CONFIRMED status
      this.data = response.filter(x => x.status == JJDisputeStatus.Confirmed);
      this.dataSource.data = this.data;

      this.data.forEach(x => {
        x.jjAssignedToName = this.jjDisputeService.jjList.filter(y => y.idir === x.jjAssignedTo)[0]?.name;
        x.vtcAssignedToName = this.jjDisputeService.jjList.filter(y => y.idir === x.vtcAssignedTo)[0]?.name;
      });

      // initially sort by decision date within status
      this.dataSource.data = this.dataSource.data.sort((a: JJDispute, b: JJDispute) => {
        if (a.jjDecisionDate > b.jjDecisionDate) { return 1; } else { return -1; }
      });
    });
  }
}

export interface JJDisputeView extends JJDispute {
  vtcAssignedToName: string;
  jjAssignedToName: string;
}