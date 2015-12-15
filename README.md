# starfighter-client

Wrapper for starfighter's REST API.

So far the documented REST API is covered, but I still need to add in ability to restart levels etc.

I've added websocket support for quotes and will add in support for trades when the service settles down a bit and I can test properly.

I've done minimal testing on this, I'll do more as the service becomes more stable.

## Usage

All of the calls are asynchronous and use RxJava (https://github.com/ReactiveX/rxjava/wiki).  Here are a couple of examples:

### Synchronous quote
    StarfighterConnection connection = new StarfighterConnection("auth1234");
    StockQuote quote = connection.getQuoteForStock(stock, venue).toBlocking().single();
  
### Asynchronous quote
    StarfighterConnection connection = new StarfighterConnection("auth1234");
    connection.getQuoteForStock(stock, venue).subscribe(new Subscriber<StockQuote>() {
        @Override
        public void onCompleted() {
        
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onNext(StockQuote stockQuote) {
            // next step here
        }
    });
    
### Subscribe to quote updates
    StarfighterConnection connection = new StarfighterConnection("auth123");
        connection.subscribeToQuotes("venue1", "account1", "symbol")
                .subscribe(new Subscriber<StockQuote>() {
                    @Override
                    public void onCompleted() {
                        
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(StockQuote stockQuote) {
                      // new quotes will appear here
                    }
                });
                
These are very basic examples of RxJava - read the documentation for more info.
