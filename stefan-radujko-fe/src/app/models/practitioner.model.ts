import { Organization } from "./organization.model";

export interface Practitioner{
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
  qualification: string;
  username: string | null;
  password: string | null;
  organization: Organization;
}
