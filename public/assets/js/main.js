'use strict';

let router;

$(() => {

  router = new Navigo('/', false);
  router.on('ok', () => {
    console.log('Page Ok');
  })
  .on('*', () => {
    if (router.lastRouteResolved().url === origin) {
      console.log('Page d\'acceuil');
    } else {
      console.log('Page not found');
    }
  })
  .resolve();

});