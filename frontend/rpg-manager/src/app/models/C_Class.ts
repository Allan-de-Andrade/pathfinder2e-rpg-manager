import { Attribute } from "./Attributes";
import { SkillType } from "./SkillType";
import { Talent } from "./Talent";

export class C_Class{
    id:Number;
    name:string;
    description:string;
    healthPoints:number;
    primaryAttribute: Attribute ;
    talents:Talent[]
    fixedSkills: SkillType[] = [];
    skillsExtra:number
    constructor(id:Number, name:string, description:string, healthPoints:number, primaryAttribute: Attribute, fixedSkills: SkillType[],talent:Talent[],skillsExtra:number) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.healthPoints = healthPoints;
        this.primaryAttribute = primaryAttribute;
        this.fixedSkills = fixedSkills;
        this.talents = talent  ;
        this.skillsExtra = skillsExtra
    }
}