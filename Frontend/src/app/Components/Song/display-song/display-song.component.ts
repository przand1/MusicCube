import { Component, OnInit, SecurityContext } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { SongService } from '../../../Services/song.service';
import {Song} from '../../../Class/Song';
import { TokenStorageService } from 'src/app/Services/token-storage.service';
import { FavoriteListsService } from 'src/app/Services/favorite-lists.service';
import { RateService } from 'src/app/Services/rate.service';
import { Rate } from 'src/app/Class/Rate';
import { CommentService } from 'src/app/Services/comment.service';
import { CommentClass } from 'src/app/Class/CommentClass';
import { DomSanitizer } from '@angular/platform-browser';
import { Genre } from 'src/app/Class/Genre';
import {UserSongStatus} from '../../../Class/UserSongStatus';
import {UserSongStatusService} from '../../../Services/user-song-status.service';

@Component({
  selector: 'app-display-song',
  templateUrl: './display-song.component.html',
  styleUrls: ['./display-song.component.css']
})
export class DisplaySongComponent implements OnInit {

  song: Song;
  rate: Rate;
  comment: CommentClass;
  allComments: CommentClass[];

  genre: Genre;

  isLogged: boolean;
  isFavorite: boolean;
  isRated: boolean;
  isMusicVideo: boolean;
  isListened: boolean;
  isToListen: boolean;

  selectOption: string;

  commentContent: string;
  userName: string;


  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private songService: SongService,
    private tokenStorage: TokenStorageService,
    private favoriteListsService: FavoriteListsService,
    private rateService: RateService,
    private sanitizer: DomSanitizer,
    private commentService: CommentService,
    private songStatusService: UserSongStatusService
    ) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.isMusicVideo = false;
    this.getSong();
    this.getComments();
    this.isLogged = false;
    this.commentContent = '';
    this.userName = this.tokenStorage.getUsername();
    if (this.tokenStorage.getToken()) {
      this.isLogged = true;
      this.checkIfIsFavorite();
      this.checkIfIsRated();
    }
  }

  private checkIfIsFavorite() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.favoriteListsService.existSongInUserFavorites(this.userName, id).subscribe(
      res => {
        this.isFavorite = res;
      },
      err => {
        console.error(err);
      }
    );
  }

  private checkIfIsRated() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.rateService.getByUserNameAndSongId(this.userName, id).subscribe(
      res => {
        console.log('This song was rated by user');
        this.rate = new Rate(res);
        this.isRated = true;
        this.selectOption = this.rate.getRate().toString();
      },
      err => {
        console.log('User hasn\'t rated this song yet');
        this.isRated = false;
        this.selectOption = '0';
      }
    );
  }

  private getSong() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.songService.getById(id).subscribe(
      res => {
        this.song = new Song(res);
        if (this.song.getMusicVideoUrl() !== '' || this.song.getMusicVideoUrl() !== null) {
          this.isMusicVideo = true;
        }
        if (this.song.getGenre() === null) {
          this.genre = new Genre();
          this.song.setGenre(this.genre);
        }
        console.log('display-song-component received: ', res);
      },
      err => console.error(err));
  }

  setScore() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (this.isRated && this.selectOption === '0') {
      this.rateService.delete(this.rate.getId()).subscribe(
        res => {
          console.log('Score was deleted');
          this.isRated = false;
        },
        err => {
          console.error('Error has occurred');
        }
      );
    } else if (!this.isRated && this.selectOption === '0') {

    } else if (!this.isRated) {
      this.rateService.createSongRate(this.userName, id, parseInt(this.selectOption)).subscribe(
        res => {
          this.rate = new Rate(res);
          this.isRated = true;
          console.log('New score was set');
        },
        err => {
          console.error('Error has occurred');
        }
      );
    } else if (this.isRated) {
      this.rateService.edit(this.rate.getId(), parseInt(this.selectOption)).subscribe(
        res => {
          this.rate.setRate(parseInt(this.selectOption));
          console.log('Score has been changed');
        },
        err => {
          console.error('Error has occurred');
        }
      );
    }
  }

  toFavorite() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (this.isFavorite) {
      this.favoriteListsService.deleteSongToFavorites(this.userName, id).subscribe(
        res => {
          console.log('Song succesfully deleted from favorite');
        },
        err => {
          console.error('Error has occurred');
        }
      );
    } else {
      this.favoriteListsService.addSongToFavorites(this.userName, id).subscribe(
        res => {
          console.log('Song successfully added to favorite');
        },
        err => {
          console.error('Error has occurred');
        }
      );
    }
  }

  cleanComment() {
    this.commentContent = '';
  }

  sendComment() {
    const id = +this.route.snapshot.paramMap.get('id');

    if (this.commentContent.length > 2) {
      this.comment = new CommentClass();
      this.comment.setCommentContent(this.commentContent);
      this.comment.setCommentDate(new Date());
      this.comment.setSong(this.song);
      this.comment.setWasEdited(false);
      this.commentService.create(this.comment, this.userName).subscribe(
        res => {
          console.log('Comment was successfully created');
          window.location.reload();
        },
        err => {
          window.alert('Error has occured');
        }
      );
    }
  }


  getComments() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.commentService.getBySongId(id).subscribe(res => {
      console.log('display-song-component comments, received: ', res);
      this.allComments = res.map(el => new CommentClass(el));
    },
    err => console.error(err));
  }

  deleteComment(commentId: number) {
    const id = +this.route.snapshot.paramMap.get('id');
    this.commentService.delete(commentId).subscribe(
      res => {
        console.log('Comment was successfully deleted');
        window.location.reload();
      },
      err => {
        window.alert('Error has occurred');
      }
    );
  }


  private checkIfIsListened() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.songStatusService.getByUserAndSong(this.userName, id).subscribe(
      res => {
        console.log(`UserSongStatus found`, res);
        const songStatus = new UserSongStatus(res);
        this.isToListen = songStatus.toListen;
        this.isListened = songStatus.listened;
      },
      err => {
        if (err.status === 404) {
          console.error(`UserSongStatus not found`);
        }
        console.error(err);
      }
    );
  }
  toToListen() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (this.isToListen) {
      this.songStatusService.update(this.userName, id, false, false).subscribe(
        res => console.log(res),
        err => console.error(err)
      );
    } else {
      this.isListened = false;
      this.songStatusService.update(this.userName, id, false, true).subscribe(
        res => console.log(res),
        err => console.error(err)
      );
    }
  }

  toListened() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (this.isListened) {
      this.songStatusService.update(this.userName, id, false, false).subscribe(
        res => console.log('Added to listened songRatingDtos', res),
        err => console.error(err)
      );
    } else {
      this.isToListen = false;
      this.songStatusService.update(this.userName, id, true, false).subscribe(
        res => console.log('Added to toListen songRatingDtos', res),
        err => console.error(err)
      );
    }
  }
}
