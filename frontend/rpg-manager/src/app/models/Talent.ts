export class Talent{
    id:number
    name:string
    description:string
    levelRequired:number
    
    constructor(id:number,name:string,description:string,levelRequired:number){
        this.id = id;
        this.name = name;
        this.description = description;
        this.levelRequired = levelRequired
    }
}