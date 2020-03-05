export class Creator {

  public static createPrice(val: number): string {
    return val?.toFixed(2).replace('.', ',');
  }



}
