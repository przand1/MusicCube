import { Component, OnInit } from '@angular/core';
import { CityService } from 'src/app/Services/city.service';
import { City } from 'src/app/Class/City';
import { Country } from 'src/app/Class/Country';

@Component({
  selector: 'app-add-city',
  templateUrl: './add-city.component.html',
  styleUrls: ['./add-city.component.css']
})
export class AddCityComponent implements OnInit {

  private city: City;
  private cityName: string;

  private isCountryClicked: boolean;

  constructor(private cityService: CityService) { }

  ngOnInit() {
    this.cityName = '';
    this.city = new City();
    this.isCountryClicked = false;
  }

  searchCountry() {
    this.isCountryClicked = true;
  }

  countryEventHandler($event: any) {
    this.city.setCountry($event);
  }

  addCity() {
    if (this.cityName === '') {
      window.alert('Incomplete input');
    } else {
      this.city.setCityName(this.cityName);
      this.cityService.create(this.city).subscribe(
        res => {
          console.log('add-city-component received:');
          console.log(res);
          window.alert('City added');
        },
        err => {
          console.error(err);
          window.alert('Error occurred');
        }
      );
    }
  }

}
