import { Component,OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Hero} from '../hero';
import {HeroDetailComponent} from '../hero-detail/hero-detail.component';
import {HeroService} from '../hero.service';

@Component({
  selector: 'my-heroes',
  template:`  
    <div>
      <label>Hero name:</label> <input #heroName />
      <button (click)="add(heroName.value); heroName.value=''">
        Add
      </button>
    </div>
    <ul class="heroes">
      <li *ngFor="let hero of heroes" 
      (click)="onSelect(hero)"
      [class.selected]="hero === selectedHero">
        <span class="badge">{{hero.id}}</span> {{hero.name}}
        <button class="delete" (click)="delete(hero); $event.stopPropagation()">x</button>
      </li>
    </ul>
    <div *ngIf="selectedHero">
      <h2>
        {{selectedHero.name | uppercase}} is my hero
      </h2>
      <button (click)="gotoDetail()">View Details</button>
    </div>
  `,
  styleUrls:['./heroes.component.css']
})
export class HeroesComponent implements OnInit {
  heroes:Hero[];
  selectedHero:Hero;
  constructor(private heroService:HeroService,private router:Router){

  }
  onSelect(hero:Hero):void{
    this.selectedHero=hero;
  }
  getHeroes():void{
    this.heroService.getHeroes().then(heroes => this.heroes = heroes);
  }
  ngOnInit(){
    this.getHeroes();
  }
  gotoDetail(): void {
    this.router.navigate(['/detail', this.selectedHero.id]);
  }
  add(name:string):void{
    name=name.trim();
    if(!name) return;
    this.heroService.create(name)
      .then(hero=>{
        this.heroes.push(hero);
        this.selectedHero=null;
      });
  }
  
  delete(hero:Hero):void{
    this.heroService
      .delete(hero.id)
      .then(() => {
        this.heroes = this.heroes.filter(h => h !== hero);
        if (this.selectedHero === hero) { this.selectedHero = null; }
      });
  }
}
