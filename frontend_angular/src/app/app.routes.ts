import { Routes } from '@angular/router';
import { ChatComponent } from './pages/chat/chat.component';
import { CricketComponent } from './pages/cricket/cricket.component';
import { ImageComponent } from './pages/image/image.component';

export const routes: Routes = [
  {
    path: 'chat',
    component: ChatComponent
  },
  {
    path: '',
    redirectTo: 'chat',
    pathMatch:'full'
  },
  {
    path: 'cricket',
    component: CricketComponent
  },
  {
    path: 'image',
    component: ImageComponent
  },
];
