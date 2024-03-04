import { Organization } from "./organization.model";
import { Practitioner } from "./practitioner.model";

export interface Patient{
  id: number;
  identifier: string;
  active: boolean;
  name: string;
  surname: string;
  gender: string;
  birthDate: Date;
  adress: string;
  phone: string;
  email: string;
  deceased: boolean;
  maritialStatus: string;
  organization: Organization;
  practitioner: Practitioner;
}
