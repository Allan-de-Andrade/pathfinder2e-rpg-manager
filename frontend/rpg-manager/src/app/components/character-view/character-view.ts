import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { KeyValuePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { CharacterService } from '../../services/character-service';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';

@Component({
  selector: 'app-character-view',
  standalone: true,
  imports: [
    FormsModule,
    KeyValuePipe,

    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDividerModule,
    MatProgressBarModule
  ],
  templateUrl: './character-view.html',
  styleUrl: './character-view.css',
})
export class CharacterView implements OnInit {

  activeRoute = inject(ActivatedRoute);
  router = inject(Router);
  private service = inject(CharacterService);
  private cdr = inject(ChangeDetectorRef);
  readonly dialogue = inject(MatDialog);
  characterData: any;

  attributesName = [
    "STRENGTH",
    "DEXTERITY",
    "CONSTITUTION",
    "INTELLIGENCE",
    "WISDOM",
    "CHARISMA"
  ];

  ngOnInit(): void {

    const characterId = String(this.activeRoute.snapshot.paramMap.get('id'));

    this.service.findById(characterId).subscribe({
      next: (data) => {
        this.characterData = data;
        this.cdr.detectChanges();
      },
      error: (e) => {
        console.log(e);
        this.cdr.detectChanges();
      }
    });
  }
  openDeleteDialogue(): void {
    let id:string | null = this.activeRoute.snapshot.paramMap.get('id')
    this.dialogue.open(DeleteDialogue, { data: { characterId: id } });
  }
  
}
@Component({
  selector: 'delete-dialogue',
  templateUrl: './delete-dialogue.html',
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent],
})
export class DeleteDialogue {
  readonly dialogRef = inject(MatDialogRef<DeleteDialogue>);
  router = inject(Router);
  private service = inject(CharacterService);
  private data = inject(MAT_DIALOG_DATA);

  deleteCharacter(): void {
    let characterId:string = this.data.characterId;

    if (characterId) {
      console.log('Deleting character with ID:', characterId);
      this.service.delete(characterId).subscribe({
        next: () => {
          console.log("Hello")
          alert('Character deleted successfully');
          this.dialogRef.close();
          this.router.navigate(['/']);
        },
        error: (e) => {
          console.log("Hi")
          alert('Error deleting character: ' + e);
        }
      });
    }
  }
}