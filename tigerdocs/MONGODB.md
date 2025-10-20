## remove duplication in collection
```
  // First, remove duplicates (keeping the first occurrence)
  db.products.aggregate([
    { $group: { _id: "$productId", ids: { $push: "$_id" } } },
    { $match: { "ids.1": { $exists: true } } }
  ]).forEach(doc => {
    doc.ids.shift(); // Keep first document
    db.products.deleteMany({ _id: { $in: doc.ids } });
  });

```

## Index creation
```
db.products.createIndex({ productId: 1 }, { unique: true })
```