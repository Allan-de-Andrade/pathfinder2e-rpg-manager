import {ChangeDetectorRef, Component, inject} from '@angular/core';
import {FormBuilder, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatStepper, MatStepperModule} from '@angular/material/stepper';
import {MatChipsModule} from '@angular/material/chips';
import { MatOption, MatSelect } from '@angular/material/select';
import { CharacterService } from '../../services/character-service';
import { Character } from '../../models/Character';
import { Attribute } from '../../models/Attributes';
import { Router } from '@angular/router';
import { SkillType } from '../../models/SkillType';
import { C_Class } from '../../models/C_Class';

/**
 * @title Stepper that displays errors in the steps
 */
@Component({
  selector: 'create-character',
  templateUrl: 'create-character.html',
  styleUrl: 'create-character.css',
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: {showError: true},
    },
  ],
  imports: [
    MatStepperModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelect,
    MatOption,
    MatChipsModule
  ],
})
export class CreateCharacter {
  private _formBuilder = inject(FormBuilder);
  
  service = inject(CharacterService);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef)

  classes:C_Class[] = [];
  races:any[] = [];
  bibliographies:any[] = [];
  skillsChoiced:SkillType[] = [];
  skillsAvailable:SkillType[] =  [
    SkillType.Acrobatics,
    SkillType.Arcana,
    SkillType.Athletics,
    SkillType.Crafting,
    SkillType.Deception,
    SkillType.Diplomacy,
    SkillType.Intimidation,
    SkillType.Medicine,
    SkillType.Nature,
    SkillType.Occultism,
    SkillType.Performance,
    SkillType.Religion,
    SkillType.Society,
    SkillType.Stealth,
    SkillType.Survival,
    SkillType.Thievery
  ];
  
  ngOnInit() {
    
    this.service.listClasses().subscribe((data:C_Class[]) => {
      this.classes = data;
    });
    this.service.listRaces().subscribe((data:any) => {
      this.races = data;
    });

    this.service.listBibliographies().subscribe((data:any) => {
      this.bibliographies = data;
    });
  }

  basicInfoForm = this._formBuilder.group({
    name: ['', Validators.required],
    backstory: ['', Validators.required],
    level: ['', Validators.required],
  });
  
  attributesForm = this._formBuilder.group({
    strength: ['', Validators.required],
    dexterity: ['', Validators.required],
    constitution: ['', Validators.required],
    intelligence: ['', Validators.required],
    wisdom: ['', Validators.required],
    charisma: ['', Validators.required],
  });

  choicesForm = this._formBuilder.group({
    classId: ['', Validators.required],
    ancestryId: ['', Validators.required],
    bibliographyId: ['', Validators.required],    
  });

  skillsForm = this._formBuilder.group({  
    skill:this._formBuilder.control<SkillType[]>([],Validators.required)
  });
  
  submitCharacter() {
    if(this.attributesForm.valid && this.basicInfoForm.valid && this.choicesForm.valid){
      let attributes: Record<Attribute, number> = {
        [Attribute.STRENGTH]: Number(this.attributesForm.value.strength),
        [Attribute.DEXTERITY]: Number(this.attributesForm.value.dexterity),
        [Attribute.CONSTITUTION]: Number(this.attributesForm.value.constitution),
        [Attribute.INTELLIGENCE]: Number(this.attributesForm.value.intelligence),
        [Attribute.WISDOM]: Number(this.attributesForm.value.wisdom),
        [Attribute.CHARISMA]: Number(this.attributesForm.value.charisma)
      }
      let skills:SkillType[] = this.skillsForm.value.skill!

      let character:Character = new Character(
        String(this.basicInfoForm.value.name),
        Number(this.basicInfoForm.value.level),
        String(this.basicInfoForm.value.backstory),
        Number(this.choicesForm.value.classId),
        Number(this.choicesForm.value.bibliographyId),
        Number(this.choicesForm.value.ancestryId),
        attributes,
        skills
      )
      
      this.service.create(character).subscribe({  
          next(data:any) {
            console.log(data)
          },
          error(e){
            console.log(e)
            console.log(character)
            alert(e)
          }
        }
      )
      this.router.navigate(["/"]);
    }
  }   
}
