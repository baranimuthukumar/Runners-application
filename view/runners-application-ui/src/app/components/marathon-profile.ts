export class MarathonProfile{
    serialNo:any;
    registerId: any;
    marathonId:any;
    eventName: any;
    profileId: any;
    year: any;
    bestTime:any;
    futureOrpast:any;
    distance:any;
    paymentReference:any;
    podium:any;
    room:any;
    travel:any;
  }

  export class RunnerProfile{
    serialNo:any;
    profileId: any;
    userName: any;
    email:any;
    password:any;
    age: any;
    dob:any;
    contactNumber:any;
   // clubName:any;
    activeMember:any;
    ageCategory:any;
    personalBest:any;
    bloodGroup:any;
    tshirtSize:any;
    tshirtIssued:any;
    emergencyContactName:any;
    profession:any;
    stravaLink:any;
    interestsOtherThanRunning:any;
    tellAboutyourself:any;
    emergencyContactNumber:any;
    marathonProfileList: MarathonProfile[] = [];
  } 

  export class MarathonEvent{
    marathonId:any;
    eventName:any;
    eventYear:any;
  }

  export class LoginDeatils{
    userName:any;
    password:any;
    valid:any;
    profileId:any;
  }

  export class wholeYearData{
    athleteId:any;
    userName:any;
    totalKilometer:any;
    decimalKilometer:any;
    code:any;
    expiresAt:any;
    refreshToken:any;
  }

  export class TopperData{
    rank:any;
    name:any;
    distance:any;
    monthlyMap:any={};
    selectMonth:any;
  }