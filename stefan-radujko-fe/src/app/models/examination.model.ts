import { Organization } from "./organization.model";
import { Patient } from "./patient.model";
import { ServiceType } from "./service-type.model";

export interface Examination{
  id: number;
  identifier: string;
  patient: Patient;
  organization: Organization;
  status: string;
  serviceType: ServiceType;
  priority: string;
  startDate: Date;
  endDate: Date;
  diagnosis: string;
}

