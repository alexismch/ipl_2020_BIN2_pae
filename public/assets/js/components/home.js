'use strict';

function getTemplate() {
  return `<div class="carousel slide d-block" data-ride="carousel" id="template-carousel">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img alt="template-0" src="/assets/img/apple-693971__340.jpg"/>
        <div class="carousel-caption d-block">
          <h5>TYPE D AMENAGEMENT</h5>
        </div>
      </div>
      <div class="carousel-item">
        <img alt="template-1" src="/assets/img/apple-tree-blossom-2292598__340.jpg"/>
        <div class="carousel-caption d-block">
          <h5>TYPE D AMENAGEMENT</h5>
        </div>
      </div>
      <div class="carousel-item">
        <img alt="template-2" src="/assets/img/espalier-60827__340.jpg"/>
        <div class="carousel-caption d-block">
          <h5>TYPE D AMENAGEMENT</h5>
        </div>
      </div>
      <div class="carousel-item">
        <img alt="template-3" src="/assets/img/garden-910946__340.jpg"/>
        <div class="carousel-caption d-block">
          <h5>TYPE D AMENAGEMENT</h5>
        </div>
      </div>
      <div class="carousel-item">
        <img alt="template-4" src="/assets/img/JardinAgrement.jpg"/>
        <div class="carousel-caption d-block">
          <h5>TYPE D AMENAGEMENT</h5>
        </div>
      </div>
      <div class="carousel-item">
        <img alt="template-5" src="/assets/img/white-1547092__340.jpg"/>
        <div class="carousel-caption d-block">
          <h5>TYPE D AMENAGEMENT</h5>
        </div>
      </div>
    </div>
    <a class="carousel-control-prev" data-slide="prev" href="#template-carousel" role="button">
      <i aria-hidden="true" class="fas fa-angle-left"></i>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" data-slide="next" href="#template-carousel" role="button">
      <i aria-hidden="true" class="fas fa-angle-right"></i>
      <span class="sr-only">Next</span>
    </a>
  </div>`;
}

function createView() {
  const $page = $(getTemplate());

  return $page;
}

export function getHomePage() {
  return {
    getTitle: () => 'Accueil',
    getView: createView
  }
}