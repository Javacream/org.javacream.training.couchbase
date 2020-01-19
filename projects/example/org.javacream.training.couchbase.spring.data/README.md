# Setup

1. Import Bucket travel-sample
2. Create design document airlines with view allAirlines: function (doc, meta) {if(doc.type == "airline"){emit(meta.id, null);}}