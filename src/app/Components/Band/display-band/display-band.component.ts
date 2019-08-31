import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { BandService } from '../../../Services/band.service';
import {Band} from '../../../Class/Band';
import {Album} from '../../../Class/Album';
import {ArtistActivity} from '../../../Class/ArtistActivity';
import {ArtistActivityService} from '../../../Services/artist-activity.service';
import {ArtistActivityDisplay} from '../../../Class/ArtistActivityDisplay';
import { TokenStorageService } from 'src/app/Services/token-storage.service';
import { FavoriteListsService } from 'src/app/Services/favorite-lists.service';

@Component({
  selector: 'app-display-band',
  templateUrl: './display-band.component.html',
  styleUrls: ['./display-band.component.css']
})
export class DisplayBandComponent implements OnInit {

  band: Band;
  albums: Album[];
  lnp: ArtistActivity[];
  artistDisplays: ArtistActivityDisplay[];

  private isLogged: boolean;
  private isFavorite: boolean;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private bandService: BandService,
    private activityService: ArtistActivityService,
    private tokenStorage: TokenStorageService,
    private favoriteListsService: FavoriteListsService) {
    this.artistDisplays = [];
  }

  ngOnInit() {
    this.getBand();
    this.isLogged = false;
    if (this.tokenStorage.getToken()) {
      this.isLogged = true;
      this.checkIfIsFavorite();
    }
  }


  private checkIfIsFavorite() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.favoriteListsService.existBandInUserFavorites(this.tokenStorage.getUsername(), id).subscribe(
      res => {
        this.isFavorite = res;
      },
      err => {
        console.log(err);
      }
    );
  }

  private getBand() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.bandService.getById(id).subscribe(
      res => {
        console.log('display-band-component received band: ', res);
        this.band = new Band(res);
        this.getAlbums();
        this.getMembers();
      },
      err => console.error(err));
  }
  private getAlbums() {
    this.bandService.getBandAlbums(this.band.getId()).subscribe(res => {
      console.log('display-band-component received albums: ', res);
      this.albums = res.map(el => new Album(el));
    },
      err => console.error(err));
  }
  private getMembers() {
    this.activityService.getByBandId(this.band.id).subscribe(
      res => {
        console.log('display-band-component received artists activities: ', res);
        this.lnp = res.map(el => new ArtistActivity(el));
        this.handleActivity();
      },
      err => console.error(err)
    );
  }
  /*
    Puts all activity periods of one artist together, allowing us to display them next to his name
   */
  private handleActivity() {
    const presentIds = [];
    this.lnp.forEach(el => {
      const periodString = ArtistActivityDisplay.buildPeriodString(el);

      const index = presentIds.indexOf(el.artist.id);
      if (index >= 0) {
        this.artistDisplays[index].addToPeriods(periodString);
        if (el.isActive) {
          this.artistDisplays[index].isCurrent();
        }
      } else {
        const aad = new ArtistActivityDisplay(el.artist.id, el.artist.stageName);
        presentIds.push(el.artist.id);
        aad.addToPeriods(periodString);
        if (el.isActive) {
          aad.isCurrent();
        }
        this.artistDisplays.push(aad);
      }
    });
  }

  toFavorite() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (this.isFavorite) {
      this.favoriteListsService.deleteBandToFavorites(this.tokenStorage.getUsername(), id).subscribe(
        res => {
          console.log('Band successfully deleted from favorites');
        },
        err => {
          window.alert('Error has occurred');
        }
      );
    } else {
      this.favoriteListsService.addBandToFavorites(this.tokenStorage.getUsername(), id).subscribe(
        res => {
          console.log('Band successfully added to favorites');
        },
        err => {
          window.alert('Error has occurred');
        }
      );
    }
  }
}