import { AbstractControl } from "@angular/forms";



export function dateNaissanceValidator(control: AbstractControl): { [key: string]: boolean } | null {

    if (control?.value) {
        const today = new Date();
        const dateToCheck = new Date(control.value);
        if (dateToCheck > today) {
            return { 'InvalidDate': true }
        }
    }
    return null;
}

