import { setRemoteDefinitions } from '@nrwl/angular/mf';

const manifestPath = process.env['NODE_ENV'] === 'production' ? '/assets/module-federation.manifest.prod.json' : '/assets/module-federation.manifest.dev.json';

fetch(manifestPath)
  .then((res) => res.json())
  .then((definitions) => setRemoteDefinitions(definitions))
  .then(() => import('./bootstrap').catch((err) => console.error(err)));
