import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms'
import { dateNaissanceValidator } from '../shared/person-dateNaissance.validator';
import { listProfession } from "../shared/list-profession";
import { PersonService } from '../service/person.service';
import { Personne } from '../model/personne';

@Component({
  selector: 'app-add-person',
  templateUrl: './add-person.component.html',
  styleUrls: ['./add-person.component.css']
})
export class AddPersonComponent implements OnInit {

  public listProfession: any;
  public person: Personne = new Personne();
  public base64Image: string | ArrayBuffer = null;

  constructor(private fb: FormBuilder, private personService: PersonService) {
    this.listProfession = listProfession;

  }

  ngOnInit(): void {

  }




  personneForm = this.fb.group({
    nom: ['', [Validators.required, Validators.minLength(1)]],
    nomAr: ['', [Validators.required, Validators.minLength(1), Validators.pattern("^[\u0600-\u06FF]+$")]],
    prenom: ['', [Validators.required, Validators.minLength(1)]],
    prenomAr: ['', [Validators.required, Validators.minLength(1), Validators.pattern("^[\u0600-\u06FF]+$")]],
    CIN: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(8)]],
    profession: ['', [Validators.required]],
    dateDeNaissace: [null, [Validators.required, dateNaissanceValidator]],
    typeCarte: ['', [Validators.required]],
    image: [null, [Validators.required]],

  });


  
  selectFile(event: any) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      reader.onload = (event: ProgressEvent) => {
        this.base64Image = (<FileReader>event.target).result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
  }
  


  savePerson() {

    this.person.nom = this.nom?.value;
    this.person.nomAr = this.nomAr?.value;
    this.person.prenom = this.prenom?.value;
    this.person.prenomAr = this.prenomAr?.value;
    this.person.cin = this.CIN?.value;
    this.person.dateDeNaissace = this.dateDeNaissace?.value;
    this.person.profession = this.profession?.value;
    this.person.base64Image = this.base64Image;
    this.person.typeCarte = this.typeCarte?.value;
    this.save();
    
  }

  save() {

    this.personService.save(this.person).subscribe(
      (res) => {
      console.log(res);
         this.downLoadFile(res, "application/pdf");
      }
    );
    
  

  }
  
    downLoadFile(data: any, type: string) {
    
        let blob = new Blob([data], { type: 'application/pdf'});
        let url = window.URL.createObjectURL(blob);
        let pwa = window.open(url);
        if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
            alert( 'Please disable your Pop-up blocker and try again.');
        }
    }
    
    
  onSubmit() {
    this.savePerson();
  }

  get nom() {
    return this.personneForm.get('nom');
  }

  get nomAr() {
    return this.personneForm.get('nomAr');
  }

  get prenom() {
    return this.personneForm.get('prenom');
  }
  get prenomAr() {
    return this.personneForm.get('prenomAr');
  }

  get CIN() {
    return this.personneForm.get('CIN');
  }

  get profession() {
    return this.personneForm.get('profession');
  }


  get dateDeNaissace() {
    return this.personneForm.get('dateDeNaissace');
  }


  get typeCarte() {
    return this.personneForm.get('typeCarte');
  }


  get image() {
    return this.personneForm.get('image');
  }

}
