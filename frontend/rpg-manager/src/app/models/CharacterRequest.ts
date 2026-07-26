import { Attribute } from "./Attributes";
import { SkillType } from "./SkillType";

export class Character{
    name: string;
    level:number
    backstory:string;
    classId:number;
    bibliographyId:number;
    ancestryId:number;
    attributes:Record<Attribute, number>;
    skillsExtra:SkillType[] | undefined;
    constructor(name: string,level:number,backstory:string, classId:number, bibliographyId:number, ancestryId:number, attributes:Record<Attribute, number>,skills:SkillType[] | undefined) {
        this.name = name;
        this.level = level;
        this.backstory = backstory;
        this.classId = classId;
        this.bibliographyId = bibliographyId;
        this.ancestryId = ancestryId;
        this.attributes = attributes;
        this.skillsExtra = skills
    }
}