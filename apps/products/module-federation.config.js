module.exports = {
  name: 'products',
  exposes: {
    './Module': 'apps/products/src/app/remote-entry/entry.module.ts',
  },
};
