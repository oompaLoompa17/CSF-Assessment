import { Component, inject, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Customer, MenuItem } from '../models';
import { RestaurantService } from '../restaurant.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit{

  // TODO: Task 3
  protected sub$!: Subscription
  protected order!: MenuItem[]
  protected finalOrder: MenuItem[] = []
  protected customers!: Customer[]
  protected form!: FormGroup
  protected totalCost: number = 0

  constructor(private router: Router, private fb: FormBuilder, private restSvc: RestaurantService){}

  ngOnInit(): void {
    this.createForm()
    this.order = this.restSvc.sendOrder()
    this.calculateTotal()
  }

  calculateTotal(){
    for(let i=0; i < this.order.length; i++){
      this.totalCost += this.order[i].price*this.order[i].quantity
    }
  }

  startOver(){
    this.router.navigate(['/'])
  }

  submit(){
    const formValue = this.form.value
    for (let i=0; i < this.order.length; i++){
      if (this.order[i].quantity > 0){
        this.finalOrder.push(this.order[i])
      }
    }
    this.restSvc.submitOrder(formValue, this.finalOrder)
  }

  createForm(){
    this.form = this.fb.group({
      username: this.fb.control<string>('', [Validators.required]),
      passsword: this.fb.control<string>('', [Validators.required]),
    })
  }
}
