import { InjectionToken } from "@angular/core";

export const LocalStorageToken = new InjectionToken('LocalStorageToken', {
    providedIn: 'root',
    factory: () => window.localStorage
});