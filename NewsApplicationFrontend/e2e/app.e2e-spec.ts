import { browser, by, element } from 'protractor';
import { AppPage } from './app.po';
import { protractor } from 'protractor/built/ptor';
import { async } from 'q';

describe('frontend App', () => {
    let page:AppPage;

    beforeEach(() => {
        page=new AppPage();
    });

    it('should display Title', ()=>{
        page.navigateTo();
        expect(browser.getTitle()).toEqual('NewsApplicationFrontend');
    });

    it('should be redirected to /login route on opening the application',() => {
        expect(browser.getCurrentUrl()).toContain('/')
    });

    it('should be redirected to /register route' ,() => {
        browser.element(by.css('.register-button')).click()
        expect(browser.getCurrentUrl()).toContain('/register')
    });

   it('should be redirected to register page and register as an Admin User' ,() => {
        browser.element(by.id('firstname')).sendKeys('AdminUser');
        browser.element(by.id('lastname')).sendKeys('AdminUserLastName');
        browser.element(by.id('userId')).sendKeys('admin123');
        browser.element(by.id('password')).sendKeys('admin123');
        browser.driver.sleep(1000);
        browser.element(by.id('userRole')).click();
        browser.driver.sleep(5000);
        browser.element(by.css('.register-user')).click();
        browser.driver.sleep(2000);
        expect(browser.getCurrentUrl()).toContain('/login');
    });

    it('should be able to login as an admin user and navigate to trending news',() => {
        browser.element(by.id('userId')).sendKeys('admin123');
        browser.element(by.id('password')).sendKeys('admin123');
        browser.element(by.css('.login-user')).click();
        browser.driver.sleep(1000);
        expect(browser.getCurrentUrl()).toContain('/news/trending_wise');      
    });

    
    it('should be able to search for all the news',() => {
        browser.driver.sleep(2000);
        browser.element(by.css('.search-button')).click();
        expect(browser.getCurrentUrl()).toContain('/news/search');
        browser.driver.sleep(2000);
        browser.element(by.css('.search-button-input')).sendKeys('Gandhi');
        browser.driver.sleep(2000);
        browser.element(by.css('.search-button-input')).sendKeys(protractor.Key.ENTER);
        browser.driver.sleep(2000);
        const searchItems = element.all(by.css('.newsAuthor'));
        browser.driver.sleep(2000);
    });

    it('should be able to see all the trending news',() => {
        browser.driver.sleep(2000);
        browser.element(by.id('top-button')).click();
        expect(browser.getCurrentUrl()).toContain('/news/trending_wise');    
        browser.driver.sleep(2000);
    });

    it('should be able to add news into FavouriteNewsList', async() => {
        browser.driver.manage().window().maximize();
        browser.driver.sleep(2000);
        const searchItems=element.all(by.css('.news-thumbnail'));
        searchItems.get(0).click();
        browser.driver.sleep(2000);
        browser.element(by.css('.addButton')).click();
        browser.driver.sleep(5000);
    });

    it('should be able to see the added news inside the FavouriteNewsList',() => {
        browser.driver.manage().window().maximize();
        browser.driver.sleep(2000);
        browser.element(by.id('watchlist')).click();
        browser.driver.sleep(2000);
        expect(browser.getCurrentUrl()).toContain('news/watchlist');  
        browser.driver.sleep(1000);  
    });
    
    it('should be able to logout from the application' ,() => {
        browser.element(by.css('.logout-button')).click();
        expect(browser.getCurrentUrl()).toContain('/login');
    });

    it('should be redirected to /register route' ,() => {
        browser.element(by.css('.register-button')).click()
        expect(browser.getCurrentUrl()).toContain('/register')
    });

    it('should be redirected to register page and register as an Normal User' ,() => {
        browser.element(by.id('firstname')).sendKeys('NormalUser');
        browser.element(by.id('lastname')).sendKeys('NormalUserLastName');
        browser.element(by.id('userId')).sendKeys('user123');
        browser.element(by.id('password')).sendKeys('user123');
        browser.driver.sleep(1000);
        browser.element(by.css('.register-user')).click();
        browser.driver.sleep(2000);
        expect(browser.getCurrentUrl()).toContain('/login');
    });

    it('should be able to login as a normal user and navigate to category wise news',() => {
        browser.element(by.id('userId')).sendKeys('user123');
        browser.element(by.id('password')).sendKeys('user123');
        browser.element(by.css('.login-user')).click();
        browser.driver.sleep(1000);
        expect(browser.getCurrentUrl()).toContain('/news/trending_wise');      
    });

    it('should be able to search for all the news',() => {
        browser.driver.sleep(2000);
        browser.element(by.css('.search-button')).click();
        expect(browser.getCurrentUrl()).toContain('/news/search');
        browser.driver.sleep(2000);
        browser.element(by.css('.search-button-input')).sendKeys('Google');
        browser.driver.sleep(2000);
        browser.element(by.css('.search-button-input')).sendKeys(protractor.Key.ENTER);
        browser.driver.sleep(2000);
        const searchItems = element.all(by.css('.newsAuthor'));
        browser.driver.sleep(2000);
    });

    it('should be able to see all the trending news',() => {
        browser.driver.sleep(2000);
        browser.element(by.id('top-button')).click();
        expect(browser.getCurrentUrl()).toContain('/news/trending_wise');    
        browser.driver.sleep(2000);
    });

    it('should be able to add news into FavouriteNewsList', async() => {
        browser.driver.manage().window().maximize();
        browser.driver.sleep(2000);
        const searchItems=element.all(by.css('.news-thumbnail'));
        searchItems.get(0).click();
        browser.driver.sleep(2000);
        browser.element(by.css('.addButton')).click();
        browser.driver.sleep(5000);
    });

    it('should be able to see the added news inside the FavouriteNewsList',() => {
        browser.driver.manage().window().maximize();
        browser.driver.sleep(2000);
        browser.element(by.id('watchlist')).click();
        browser.driver.sleep(2000);
        expect(browser.getCurrentUrl()).toContain('news/watchlist');  
        browser.driver.sleep(1000);  
    });
    
    it('should be able to logout from the application' ,() => {
        browser.element(by.css('.logout-button')).click();
        expect(browser.getCurrentUrl()).toContain('/login');
    });
});