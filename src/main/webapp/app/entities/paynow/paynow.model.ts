export interface IPaynow {
  id?: number;
  cik?: string | null;
  ccc?: string | null;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
}

export class Paynow implements IPaynow {
  constructor(
    public id?: number,
    public cik?: string | null,
    public ccc?: string | null,
    public name?: string | null,
    public email?: string | null,
    public phone?: string | null
  ) {}
}

export function getPaynowIdentifier(paynow: IPaynow): number | undefined {
  return paynow.id;
}
