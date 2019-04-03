import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { News } from '../../news';
import { NewsService } from '../../news.service';

@Component({
  selector: 'app-newsdialog',
  templateUrl: './newsdialog.component.html',
  styleUrls: [
    './newsdialog.component.css'
    ]
})

//Component class for handling the pop-up dialog box before updating the news description
export class NewsDialogComponent implements OnInit {

    news:News;
    description:string;
    actionType:string;

    constructor(public snackBar : MatSnackBar, public dialogRef : MatDialogRef<NewsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data : any, private newsService : NewsService ) {  
        this.description=data.obj.description;
        this.news=data.obj;
        this.actionType=data.actionType;
    }
    ngOnInit() {     
    }
    //Cancel button logic here
    onNoClick(){
        this.dialogRef.close();
    }
    //Update button logic 
    updateDescription(){
        console.log("Description is: ",this.description);
        this.news.description=this.description;
        this.dialogRef.close();
        //Invoking the description updating service logic
        this.newsService.updateNewsDescription(this.news).subscribe(news=>{
            this.snackBar.open("News description updated successfully",'',{
                duration:3000
            });
        });
    }
}