import { Examination } from "./examination.model";
import { Practitioner } from "./practitioner.model";

export interface ExaminationPractitioner{
  id: number;
  examination: Examination;
  practitioner: Practitioner;
}
