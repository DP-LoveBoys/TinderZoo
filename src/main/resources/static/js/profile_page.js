"use strict";



const generalBtn = document.querySelector("#generalBtn");
const hobbiesBtn = document.querySelector("#hobbiesBtn");
const preferencesBtn = document.querySelector("#preferencesBtn");

const generalDiv = document.querySelector("#generals");
const hobbieDiv = document.querySelector("#hobbies");
const prefsDiv = document.querySelector("#preferences");

const img1=document.querySelector("#image0");
const img2=document.querySelector("#image1");
const img3=document.querySelector("#image2");



hobbieDiv.classList.add("hidden");
prefsDiv.classList.add("hidden");


generalBtn.addEventListener("click", function () {
  generalDiv.classList.remove("hidden");
  hobbieDiv.classList.add("hidden");
  prefsDiv.classList.add("hidden");
});

hobbiesBtn.addEventListener("click", function () {
  generalDiv.classList.add("hidden");
  hobbieDiv.classList.remove("hidden");
  prefsDiv.classList.add("hidden");
});

preferencesBtn.addEventListener("click", function () {
  generalDiv.classList.add("hidden");
  hobbieDiv.classList.add("hidden");
  prefsDiv.classList.remove("hidden");
});


img1.addEventListener("click",function(){
    console.log("Click 1");
});

img2.addEventListener("click",function(){
    console.log("Click 2");
});

img3.addEventListener("click",function(){
    console.log("Click 3");
});

