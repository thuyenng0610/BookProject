// set up Express
var express = require('express');
var app = express();
app.set('view engine', 'ejs');
const mongoose = require('mongoose');

mongoose.connect('mongodb://localhost:27017/myDatabase');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// import classes
var Book = require('./Book.js');

/***************************************/

// endpoint for creating a new book
// this is the action of the "create new book" form
app.use('/create', (req, res) => {
	// construct the Book from the form data which is in the request body
	var newBook = new Book ({
		title: req.body.title,
		author: req.body.author,
		description: req.body.description
	    });

	// save the book to the database
	newBook.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else {
		    // display the "successfull created" message
		    res.send('successfully added ' + newBook.title + ' to the database');
			// res.write("<a href=\"/all" + "\">[View all recommendations]</a>");
		}
	    } ); 
    }
    );

// endpoint for showing all the books
app.use('/all', (req, res) => {
    // find all the Event objects in the database
    console.log("app views data")
    Book.find( {}, (err, books) => {
        if (err) {
            res.type('html').status(200);
            console.log('uh oh' + err);
            res.write(err);
        } else {
            if (books.length == 0) {
                res.type('html').status(200);
                res.write('There are no books');
                res.end();
            } else {
                res.type('html').status(200);
				res.write('All book recommendations:');
				res.write('<ul>');
                // show all the events
                books.forEach( (book) => {
					res.write('<li>');
                    res.write('Book Title: ' + book.title + '<br/>');
                    res.write('Author: ' + book.author + '<br/>');
                    res.write("<a href=\"/view_book?id=" + book._id + "\">[View]</a>");
                    res.write('</li>');
                });
                res.write('</ul>');
                res.end();
            }
        }
    }).sort({ 'title': 'asc' }); // this sorts them BEFORE rendering the results
});

app.use('/allapp', (req, res) => {
    // find all the Event objects in the database
    console.log("App views data")
    Book.find( {}, (err, books) => {
        if (err) {
            res.type('html').status(200);
            console.log('uh oh' + err);
            res.write(err);
        } else {
            if (books.length == 0) {
                res.type('html').status(200);
                res.write('There are no books');
                res.end();
            } else {
                res.type('html').status(200);
                var returnArray = [];
                books.forEach( (book) => {
                    var bookObject = {
                    "title": book.title,
					"author": book.author,
                    "description": book.description
                };
                returnArray.push( bookObject );
                });
                res.json(returnArray); 
                res.end();
            }
        }
    }).sort({ 'title': 'asc' }); // this sorts them BEFORE rendering the results
});

// endpoint for viewing 1 book
app.use('/view_book', (req, res) => {
    var filter = {'_id' : req.query.id};
	Book.findOne (filter, (err, book) => {
		if (err) {
			console.log(err);
		} else {
            res.type('html').status(200);
            res.write("<span style='font-weight:bold'> Details </span> <br/>");
            res.write('Title: ' + book.title + '<br/> Author: ' + book.author 
            + '<br/> Description: ' + book.description + "<br/>");
            res.end();
		}
	});
});

app.use('/public', express.static('public'));
/*************************************************/

app.use('/', (req, res) => { res.redirect('/public/bookform.html'); } );

app.listen(3000,  () => {
    console.log('Listening on port 3000');
});
