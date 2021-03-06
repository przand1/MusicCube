import { Component, OnInit } from '@angular/core';
import { AlbumService } from 'src/app/Services/album.service';
import { Album } from 'src/app/Class/Album';

@Component({
  selector: 'app-edit-album',
  templateUrl: './edit-album.component.html',
  styleUrls: ['./edit-album.component.css']
})
export class EditAlbumComponent implements OnInit {
  selectedAlbum: Album;

  album: Album;
  albumName: string;
  length: number;
  minutes: number;
  seconds: number;
  releaseDate: Date;
  company: string;
  toMandS: number;
  covertArtLink: string;
  type: string;

  isEditSelected: boolean;


  constructor(private albumService: AlbumService) {}

  ngOnInit(): void {
    this.album = new Album();
    this.albumName = '';
    this.minutes = 0;
    this.seconds = 0;
    this.length = 0;
    this.releaseDate = null;
    this.company = '';
    this.covertArtLink = '';
    this.type = '';
  }

  albumEventHandler($event) {
    this.selectedAlbum = $event;
    this.albumName = this.selectedAlbum.albumName;
    this.album.setReleaseDate(this.selectedAlbum.releaseDate);
    this.company = this.selectedAlbum.company;
    this.length = this.selectedAlbum.albumLengthSeconds;
    this.isEditSelected = true;
    this.lengthInSecondsTodMinutesAndSeconds();
    this.covertArtLink = this.selectedAlbum.coverArtLink;
    this.type = this.selectedAlbum.type;
  }
  searchEventHandler($event) {
    this.selectedAlbum = null;
  }

  albumLengthInSeconds(): boolean {
    if (this.minutes < 0 || this.seconds < 0 || this.seconds > 59 || this.minutes === 0 && this.seconds === 0) {
      return false;
    } else {
      this.length = 60 * this.minutes + this.seconds;
      return true;
    }
  }

  lengthInSecondsTodMinutesAndSeconds() {
      this.toMandS = this.length * 100 / 60;
      this.minutes = (this.toMandS - (this.toMandS % 100)) / 100;
      this.seconds = ((this.toMandS % 100) - (this.toMandS % 1)) * 60 / 100;
      this.seconds = this.seconds - (this.seconds % 1);

  }


  delete() {
    this.albumService.delete(this.selectedAlbum.id).subscribe(
      res => {
        console.log(res);
        this.reset();
      },
      err => {
        window.alert('Error has occured');
      }
    );
  }

  update() {
    this.albumLengthInSeconds();
    if (this.albumName === this.selectedAlbum.albumName && this.releaseDate === this.selectedAlbum.releaseDate && this.length === this.selectedAlbum.albumLengthSeconds && this.company === this.selectedAlbum.company && this.covertArtLink === this.selectedAlbum.coverArtLink && this.type === this.selectedAlbum.type) {
      window.alert('You need to do some changes before update');
    } else {
      this.album.id = this.selectedAlbum.id;
      this.album.setAlbumName(this.albumName);
      this.album.setCompany(this.company);
      this.album.setAlbumLengthSeconds(this.length);
      this.album.setType(this.type);
      this.album.setCoverArtLink(this.covertArtLink);
      if (this.releaseDate) {
        this.album.setReleaseDate(this.releaseDate);
      }
      this.albumService.edit(this.album).subscribe(
        res => {
          console.log(res);
          this.reset();
        },
        err => {
          window.alert('Error has occured');
        }
      );
    }
  }

  reset() {
    this.isEditSelected = false;
    this.selectedAlbum = null;
    this.album = new Album();
    this.albumName = '';
    this.minutes = 0;
    this.seconds = 0;
    this.length = 0;
    this.releaseDate = null;
    this.company = '';
  }



}
