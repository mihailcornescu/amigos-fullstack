const UserProfile = ({key, name, age, gender, imageNumber, ...props}) => {
    return (
        <div>
            <p>{name}</p>
            <p>{age}</p>
            <img src={`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}/>
        </div>
    )
}

export default UserProfile;