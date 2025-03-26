import { InjectionToken } from "@angular/core";
import { IClientService } from "./api-client/clients/iclients.service";
import { ISnackBarManagerService } from "./isnackbar-manager.service";
import { IDialogManagerService } from "./idialog-manager.service";

export const SERVICES_TOKEN ={
    HTTP: {
        CLIENT: new InjectionToken<IClientService>('SERVICES_TOKEN.HTTP.CLIENT'),
        //SCHEDULE: new InjectionToken<IClientService>('SERVICES_TOKEN.HTTP.SCHEDULE')
    },
    SNACKBAR: new InjectionToken<ISnackBarManagerService>('SERVICES_TOKEN.SNACKBAR'),
    YES_NO_DIALOG: new InjectionToken<IDialogManagerService>('SERVICES_TOKEN.YES_NO_DIALOG')
}