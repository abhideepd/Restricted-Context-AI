export class Message{
  content:string;
  query:boolean;
  constructor(content:string, query:boolean){
    this.content=content;
    this.query=query;
  }
}
