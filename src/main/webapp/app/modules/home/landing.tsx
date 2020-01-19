import React, { Component } from 'react';

export const Landing = () => {
  return (
    <div className="landing">
      <div className="dark-overlay landing-inner text-light">
        <div className="container">
          <div className="row">
            <div className="col-md-12 text-center">
              <h1 className="display-3 mb-4">Medicine Manager</h1>
              <p className="lead"> A simple web application to manage your medication</p>
              <hr />
              <a href="/" className="btn btn-lg btn-info mr-2">
                Sign Up
              </a>
              <a href="/" className="btn btn-lg btn-light ">
                Login
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
